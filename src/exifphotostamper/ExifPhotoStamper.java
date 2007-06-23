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
import java.awt.AlphaComposite;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
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
    private Color fontBorderColor = Color.WHITE;
    private String fontName = "dialog";
    private int fontSize = 60;
    private String format = "(%Yy%Mm%Dd) %y-%m-%d";
    private String renameFormat = "%y%m%d_%SN";
    private float quality = 0.85F;
    private int corner = 3;
    private int margin = 100;
    private boolean shadow = false;
    private boolean rename = false;
    private Font font = null;

    public String getRenameFormat() {
        return renameFormat;
    }

    public void setRenameFormat(String renameFormat) {
        this.renameFormat = renameFormat;
    }

    public boolean isRename() {
        return rename;
    }

    public void setRename(boolean rename) {
        this.rename = rename;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
    public boolean isShadow() {
        return shadow;
    }
    
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
    
    public Color getFontBorderColor() {
        return fontBorderColor;
    }
    
    public void setFontBorderColor(Color fontBorderColor) {
        this.fontBorderColor = fontBorderColor;
    }
    
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
        
        font = new Font(fontName, Font.PLAIN, fontSize);
        Date start = null;
        Date shootDate = null;
        
        // start = DateFormat.getDateInstance().parse(startDate);
        // 統一格式, 不理會作業系統設定
        String[] tmpDates = startDate.split("/");
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(Integer.parseInt(tmpDates[0]), (Integer.parseInt(tmpDates[1])-1),Integer.parseInt(tmpDates[2]),0 , 0);
        start = tmpCal.getTime();
        tmpCal = null;
        tmpDates = null;
        
        File srcDir = new File(sourceDir);
        File[] imgFiles = srcDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return ((name.indexOf(".jpg")!=-1) || (name.indexOf(".jpeg") != -1));
            }
        });
        
        
        if(ui != null) ((MainFrame) ui).setFilesCount(imgFiles.length);
        
        for (int i=0; i < imgFiles.length; i++) {
            if (stop)  break;
            
            try{
                File srcFile = imgFiles[i];
                Metadata metadata = JpegMetadataReader.readMetadata(srcFile);
                Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
                if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME)) {
                    shootDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME);
                }else {
                    shootDate = new Date(imgFiles[i].lastModified());
                }
                
                String formatedString = getFormatedSN(getFormatedDateString(start, shootDate, format), (i+1), imgFiles.length);

                
                String targetName = srcFile.getName();
                if (isRename()) {
                    String formatedFile = getFormatedSN(getFormatedDateString(start, shootDate, getRenameFormat(), true), (i+1), imgFiles.length);
                    targetName = formatedFile + "." + getExtName(targetName);
                }
                File outFile = new File(targetDir, targetName);
                boolean result = processPhotoStamp(formatedString, srcFile, outFile);
                
            }catch (Exception ex) {
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            }finally {
                if(ui != null) ((MainFrame) ui).finishFileIndex(i);
            }
        }
        
        if(ui != null) ((MainFrame) ui).finishAllFiles();
    }
    
    public boolean processPhotoStamp(String formatedString, File srcFile, File outFile) {
        
        Graphics2D g = null;
        ImageWriter writer = null;
        ImageReader reader = null;
        
        try {
            
            Iterator aIterator =  ImageIO.getImageReadersByFormatName("jpeg");
            while(aIterator.hasNext()) {
                reader = (ImageReader) aIterator.next();
                //break;
                if(reader.getClass().getName().equals("com.sun.imageio.plugins.jpeg.JPEGImageReader")) break;
            }
            //System.out.println("reader = " + reader.getClass().getName());
            
            Iterator bIterator  = ImageIO.getImageWritersByFormatName("jpeg");
            while(bIterator.hasNext()) {
                writer = (ImageWriter) bIterator.next();
                //break;
                if(writer.getClass().getName().equals("com.sun.imageio.plugins.jpeg.JPEGImageWriter")) break;
            }
            //System.out.println("writer = " + writer.getClass().getName());
                        
            reader.setInput(ImageIO.createImageInputStream(srcFile));

            IIOMetadata ioMetaData = reader.getStreamMetadata();
            ImageReadParam readerParam = reader.getDefaultReadParam();
            
            // Get BufferedImage
            IIOImage iioi = (IIOImage) reader.readAll(0, readerParam);
            BufferedImage imgSrc = (BufferedImage) iioi.getRenderedImage();
            
            g = (Graphics2D) imgSrc.getGraphics();
            g.setFont(font);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
            
            AlphaComposite a1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            AlphaComposite a2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            Shape fontShape = font.createGlyphVector(frc, formatedString).getOutline();
            
            if (isShadow()) {
                g.translate(left+3, top+3);
                g.setComposite(a1);
                g.setColor(Color.BLACK);
                g.fill(fontShape);
                left = -3;
                top = -3;
                g.setComposite(a2);
            }
            
            g.translate(left, top);
            g.setColor(fontColor);
            g.fill(fontShape);
            
            if (!fontColor.equals(fontBorderColor)) {
                Stroke stroke = new BasicStroke(2);
                g.setStroke(stroke);
                g.setColor(fontBorderColor);
                g.draw(fontShape);
            }
            
            ImageWriteParam  writerParam = writer.getDefaultWriteParam();
            // JPEGImageWriteParam writerParam = (JPEGImageWriteParam)writer.getDefaultWriteParam();
            writerParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writerParam.setCompressionQuality(quality);
            //writerParam.setCompressionType("JPEG");
            ((JPEGImageWriteParam) writerParam).setOptimizeHuffmanTables(true);
            
            writer.setOutput(ImageIO.createImageOutputStream(outFile));
            writer.write(ioMetaData, iioi, writerParam);
            
        } catch (Exception ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }finally {
            g.dispose();
            reader.dispose();
            writer.dispose();
            reader = null;
            writer = null;
        }
        return true;
    }
    
    public String getFormatedDateString(Date startDate, Date endDate, String format) {
        return getFormatedDateString(startDate, endDate, format, false);
    }
    
    public String getFormatedDateString(Date startDate, Date endDate, String format, boolean prefixZero) {
        // System.out.println("Start = " + startDate + ", end = "+ endDate);
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
        formatedString = formatedString.replaceAll("%m", (prefixZero && (endMonth<10)) ? "0"+Integer.toString(endMonth) : Integer.toString(endMonth));
        formatedString = formatedString.replaceAll("%d", (prefixZero && (endDay<10)) ? "0"+ Integer.toString(endDay) : Integer.toString(endDay) );
        formatedString = formatedString.replaceAll("%H", Integer.toString(endHour));
        formatedString = formatedString.replaceAll("%i", Integer.toString(endMinute));
        formatedString = formatedString.replaceAll("%s", Integer.toString(endSecond));
        
        return formatedString;
    }
    
    public String getFormatedSN(String formatedString, int current, int total) {
        int totalDigital = Integer.toString(total).length();
        int currentDigital = Integer.toString(current).length();
        StringBuffer sb = new StringBuffer(Integer.toString(current));
        while (currentDigital<totalDigital) {
            sb.insert(0, "0");
            currentDigital++;
        }
        formatedString = formatedString.replaceAll("%SN", sb.toString());
        return formatedString;
    }
    
    public String getExtName(String filename) {
        
        String[] s = filename.split("\\.");
        return s[(s.length-1)];
    }
    
    
}
