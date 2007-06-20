/*
 * ExifPhotoStamper.java
 *
 * Created on 2007/6/17, 下午 12:43:40
 *
 */

package exifphotostamper;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author rack
 */
public class ExifPhotoStamper implements Runnable {
    
    private volatile boolean stop = false;
    private JFrame ui = null;
    private String sourceDir ="c:\\tmp\\shine";
    private String targetDir = "c:\\tmp\\shine\\out";
    private String startDate = "2006/11/02";
    private Color fontColor = Color.WHITE;
    private String fontName = "dialog";
    private int fontSize = 60;
    private String format = "(%Yy%Mm%Dd) %y-%m-%d";
    private float quality = 0.85F;
    private int corner = 3;
    private int margin = 100;

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getCorner() {
        return corner;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if(format.length() >0) this.format = format;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }
    
    public String getSourceDir() {
        return sourceDir;
    }
    
    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
    
    public String getTargetDir() {
        return targetDir;
    }
    
    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        if(startDate.length() >0) this.startDate = startDate;
    }
    
    public Color getFontColor() {
        return fontColor;
    }
    
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    public String getFontName() {
        return fontName;
    }
    
    public void setFontName(String fontName) {
        if(fontName.length()>0) this.fontName = fontName;
    }
    
    public int getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    public boolean isStop() {
        return stop;
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    public JFrame getUi() {
        return ui;
    }
    
    public void setUi(JFrame ui) {
        this.ui = ui;
    }
    
    
    
    public ExifPhotoStamper() {
    }
    
    public static void main(String[] args) {
        (new Thread(new ExifPhotoStamper())).start();
    }
    
    public void run() {
        
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        Date start = null;
        Date shootDate = null;
        
        try {
            start = DateFormat.getDateInstance().parse(startDate);
        }catch (ParseException e){
            
        }

        File srcDir = new File(sourceDir);
        File[] imgFiles = srcDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return ((name.indexOf(".jpg")!=-1) || (name.indexOf(".jpeg") != -1) || (name.indexOf(".bmp") != -1));
            }
        });
        
        
        ((MainFrame) ui).setFilesCount(imgFiles.length);
        
        for (int i=0; i < imgFiles.length; i++) {
            if (stop)  break;

            try{
                
                Metadata metadata = JpegMetadataReader.readMetadata(imgFiles[i]);
                Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
                if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME)) {
                    shootDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME);
                }else {
                    shootDate = new Date(imgFiles[i].lastModified());
                }

                String formatedString = getFormatedDateString(start, shootDate, format);

                ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName("jpeg").next();
                ImageWriteParam  writerParam = writer.getDefaultWriteParam();
                writerParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                writerParam.setCompressionQuality(quality);

                ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("jpeg").next();
                
                
                ImageInputStream iis = ImageIO.createImageInputStream(imgFiles[i]);
                reader.setInput(iis);
                IIOImage iioi = reader.readAll(0, reader.getDefaultReadParam());
                BufferedImage imgSrc =(BufferedImage) iioi.getRenderedImage();
                Graphics2D g = (Graphics2D) imgSrc.getGraphics();
                g.setColor(fontColor);
                g.setFont(font);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontRenderContext frc = g.getFontRenderContext();
                Rectangle2D rectString = font.getStringBounds(formatedString, frc);
                double height = rectString.getHeight();
                double width = rectString.getWidth();
                double imgHeight = imgSrc.getHeight();
                double imgWidth = imgSrc.getWidth();
                int top =0, left=0;
                
                switch (corner) {
                case 1:
                    left = margin;
                    top = margin;                    
                    break;
                case 2:
                    left = (int)(imgWidth-width-margin);
                    top = margin;                    
                    break;
                case 3:
                    left = (int)(imgWidth-width-margin);
                    top = (int)(imgHeight-margin);
                    break;
                case 4:
                    left = margin;
                    top = (int)(imgHeight-margin);
                    break;
                }
                
                g.drawString(formatedString, left, top);
                File outFile = new File(targetDir, imgFiles[i].getName());
                ImageOutputStream ios = ImageIO.createImageOutputStream(outFile);
                writer.setOutput(ios);
                writer.write(iioi.getMetadata(), iioi, writerParam);
                g.dispose();
                ios.close();
                iis.close();
                reader.dispose();
                writer.dispose();
                
                ((MainFrame) ui).finishFileIndex(i);
            } catch (Exception ex) {
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            }
        }
        
        ((MainFrame) ui).finishAllFiles();
    }
    
    public String getFormatedDateString(Date startDate, Date endDate, String format) {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int startYear = cal.get(Calendar.YEAR);
        int startMonth = cal.get(Calendar.MONTH)+1;
        int startDay = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(endDate);
        int endYear = cal.get(Calendar.YEAR);
        int endMonth = cal.get(Calendar.MONTH)+1;
        int endDay = cal.get(Calendar.DAY_OF_MONTH);
        int endHour = cal.get(Calendar.HOUR_OF_DAY);
        int endMinute = cal.get(Calendar.MINUTE);
        int endSecond = cal.get(Calendar.SECOND);
        
        int durationYear=0, durationMonth=0, durationDay=0;
        String formatedString = format;
        
        if (endDay>=startDay) {
            durationDay = endDay - startDay;
            int tmpMonth = (endYear-startYear)*12 + (endMonth-startMonth);
            durationYear = (int)Math.floor(tmpMonth / 12);
            durationMonth = tmpMonth % 12;
        }else {
            int tmpMonth = (endYear-startYear)*12 + (endMonth-startMonth) -1;
            durationYear = (int)Math.floor(tmpMonth / 12);
            durationMonth = tmpMonth % 12;
            int tmpY = endYear;
            int tmpM = endMonth;
            if (tmpM == 1) {
                tmpY -= 1;
                tmpM = 12;
            }else {
                tmpM -= 1;
            }
            cal.set(endYear, (endMonth-1), 1);
            long timeOfMonth = cal.getTimeInMillis();
            cal.set(tmpY, (tmpM-1), 1);
            long timeOfPrevMonth = cal.getTimeInMillis();
            durationDay = (int)( (timeOfMonth - timeOfPrevMonth)/86400000L ) - startDay + endDay ;

        }
        
        formatedString = formatedString.replaceAll("%Y", Integer.toString(durationYear));
        formatedString = formatedString.replaceAll("%M", Integer.toString(durationMonth));
        formatedString = formatedString.replaceAll("%D", Integer.toString(durationDay));
        formatedString = formatedString.replaceAll("%y", Integer.toString(endYear));
        formatedString = formatedString.replaceAll("%m", Integer.toString(endMonth));
        formatedString = formatedString.replaceAll("%d", Integer.toString(endDay));
        formatedString = formatedString.replaceAll("%H", Integer.toString(endHour));
        formatedString = formatedString.replaceAll("%i", Integer.toString(endMinute));
        formatedString = formatedString.replaceAll("%s", Integer.toString(endSecond));
                
        return formatedString;
    }
    
    
}
