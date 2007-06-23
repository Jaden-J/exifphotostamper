/*
 * MainFrame2.java
 *
 * Created on 2007年6月20日, 下午 3:19
 */

package exifphotostamper;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;


/**
 *
 * @author  rack
 */
public class MainFrame extends javax.swing.JFrame {
    
    private ComboBoxModel allSystemFonts = new DefaultComboBoxModel(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    private Color fontColor = Color.WHITE;
    private Color fontBorderColor = Color.WHITE;
    private ExifPhotoStamper stamp ;
    
    
    /** Creates new form MainFrame2 */
    public MainFrame() {
        
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            FileHandler fh = new FileHandler("exifphotostamper.log");
            Logger.getLogger("global").addHandler(fh);
        }catch(Exception ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        
        cancelBtn.setEnabled(false);
        stamp = new exifphotostamper.ExifPhotoStamper();
        stamp.setUi(this);
        java.lang.String[] cornerOpt = new java.lang.String[4];
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("exifphotostamper/messages");
        cornerOpt[0] = bundle.getString("TopLeft");
        cornerOpt[1] = bundle.getString("TopRight");
        cornerOpt[2] = bundle.getString("BottomRight");
        cornerOpt[3] = bundle.getString("BottomLeft");
        cornerCombo.setModel(new javax.swing.DefaultComboBoxModel(cornerOpt));
        loadProperties();
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        startDateLabel = new javax.swing.JLabel();
        startDate = new javax.swing.JTextField();
        sourceDirLabel = new javax.swing.JLabel();
        sourceDir = new javax.swing.JTextField();
        sourceDirBtn = new javax.swing.JButton();
        targetDirBtn = new javax.swing.JButton();
        targetDir = new javax.swing.JTextField();
        targetDirLabel = new javax.swing.JLabel();
        fontNameLabel = new javax.swing.JLabel();
        fontNameCombo = new javax.swing.JComboBox();
        fontSizeLabel = new javax.swing.JLabel();
        fontSize = new javax.swing.JTextField();
        colorBtn = new javax.swing.JButton();
        fontColorValue = new javax.swing.JTextField();
        fontColorLabel = new javax.swing.JLabel();
        cornerLabel = new javax.swing.JLabel();
        cornerCombo = new javax.swing.JComboBox();
        margin = new javax.swing.JTextField();
        pixels = new javax.swing.JLabel();
        marginLabel = new javax.swing.JLabel();
        qualityLabel = new javax.swing.JLabel();
        quality = new javax.swing.JTextField();
        qualityTips = new javax.swing.JLabel();
        format = new javax.swing.JTextField();
        formatLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        formatLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        startBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        aboutBtn = new javax.swing.JButton();
        fontBorderColorLabel = new javax.swing.JLabel();
        fontBorderColorValue = new javax.swing.JTextField();
        colorBorderBtn = new javax.swing.JButton();
        shadowCheckBox = new javax.swing.JCheckBox();
        renameCheckBox = new javax.swing.JCheckBox();
        rename = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ExifPhotoStamper v1.0.3");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("exifphotostamper/messages"); // NOI18N
        startDateLabel.setText(bundle.getString("StartDate")); // NOI18N

        startDate.setText("2006/11/02");

        sourceDirLabel.setText(bundle.getString("SourceDirectory")); // NOI18N

        sourceDirBtn.setText("...");
        sourceDirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceDirBtnActionPerformed(evt);
            }
        });

        targetDirBtn.setText("...");
        targetDirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetDirBtnActionPerformed(evt);
            }
        });

        targetDirLabel.setText(bundle.getString("TargetDirectory")); // NOI18N

        fontNameLabel.setText(bundle.getString("FontName")); // NOI18N

        fontNameCombo.setModel(allSystemFonts);

        fontSizeLabel.setText(bundle.getString("FontSize")); // NOI18N

        fontSize.setText("60");

        colorBtn.setText("...");
        colorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBtnActionPerformed(evt);
            }
        });

        fontColorValue.setEditable(false);
        fontColorValue.setText("[255,255,255]");

        fontColorLabel.setText(bundle.getString("FontColor")); // NOI18N

        cornerLabel.setText(bundle.getString("Corner")); // NOI18N

        cornerCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        margin.setText("100");

        pixels.setText(bundle.getString("Pixels")); // NOI18N

        marginLabel.setText(bundle.getString("Margin")); // NOI18N

        qualityLabel.setText(bundle.getString("Quality")); // NOI18N

        quality.setText("0.8");

        qualityTips.setText(bundle.getString("QualityTip")); // NOI18N

        format.setText("(%Yy%Mm%Dd) %y/%m/%d");

        formatLabel.setText(bundle.getString("Format")); // NOI18N

        jTextPane1.setEditable(false);
        jTextPane1.setText(bundle.getString("FormatTip")); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        formatLabel1.setText(bundle.getString("Progress")); // NOI18N

        jProgressBar1.setStringPainted(true);

        startBtn.setText(bundle.getString("StartButton")); // NOI18N
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText(bundle.getString("CancelButton")); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        aboutBtn.setText(bundle.getString("AboutButton")); // NOI18N
        aboutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutBtnActionPerformed(evt);
            }
        });

        fontBorderColorLabel.setText(bundle.getString("FontBorderColor")); // NOI18N

        fontBorderColorValue.setEditable(false);
        fontBorderColorValue.setText("[255,255,255]");

        colorBorderBtn.setText("...");
        colorBorderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBorderBtnActionPerformed(evt);
            }
        });

        shadowCheckBox.setText(bundle.getString("Shadow")); // NOI18N
        shadowCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        shadowCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        shadowCheckBox.setOpaque(false);

        renameCheckBox.setText(bundle.getString("Rename")); // NOI18N
        renameCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        renameCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        renameCheckBox.setOpaque(false);

        rename.setText("%y%m%d_%SN");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cornerLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(fontColorLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(fontNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(targetDirLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(sourceDirLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(startDateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(marginLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(formatLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(formatLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .add(renameCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                        .add(fontNameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 352, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                                        .add(fontSizeLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(fontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(sourceDir, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(sourceDirBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                                    .add(layout.createSequentialGroup()
                                        .add(targetDir, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(targetDirBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                                    .add(startDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(layout.createSequentialGroup()
                                                .add(fontColorValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(colorBtn))
                                            .add(cornerCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(40, 40, 40)
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(layout.createSequentialGroup()
                                                .add(fontBorderColorLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(fontBorderColorValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(colorBorderBtn)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .add(shadowCheckBox))))
                                .add(20, 20, 20))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                .addContainerGap())
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(margin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(pixels, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(40, 40, 40)
                                        .add(qualityLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(quality, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(qualityTips, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                                    .add(format, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                                    .add(rename, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                                .add(20, 20, 20))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(startBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(aboutBtn)
                        .add(192, 192, 192))))
            .add(layout.createSequentialGroup()
                .add(129, 129, 129)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 454, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, startDateLabel)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, startDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, sourceDirLabel)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, sourceDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, sourceDirBtn))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, targetDirLabel)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, targetDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, targetDirBtn))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(fontNameLabel)
                    .add(fontSizeLabel)
                    .add(fontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fontNameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(fontColorLabel)
                    .add(colorBtn)
                    .add(fontColorValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fontBorderColorLabel)
                    .add(colorBorderBtn)
                    .add(fontBorderColorValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, cornerLabel)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, cornerCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.CENTER, shadowCheckBox))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(marginLabel)
                    .add(qualityTips)
                    .add(quality, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(qualityLabel)
                    .add(pixels)
                    .add(margin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(format, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(rename, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(renameCheckBox)))
                    .add(formatLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(formatLabel1)
                    .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelBtn)
                    .add(aboutBtn)
                    .add(startBtn))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colorBorderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBorderBtnActionPerformed
        javax.swing.JColorChooser dialog = new JColorChooser();
        fontBorderColor = dialog.showDialog(colorBorderBtn,"", fontBorderColor);
        if (fontBorderColor != null) fontBorderColorValue.setText("[" + fontBorderColor.getRed() + "," + fontBorderColor.getGreen()+","+ fontBorderColor.getBlue()+"]");

}//GEN-LAST:event_colorBorderBtnActionPerformed

private void aboutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutBtnActionPerformed
    StringBuffer sb = new StringBuffer("ExifPhotoStamper v1.0.3 \n\n");
    sb.append("Copyright (c) 2007 Rack Lin (racklin.blogspot.com) \n");
    sb.append("Email: racklin@gmail.com \n");
    sb.append("My Chinese Blog:  http://racklin.blogspot.com/");
    javax.swing.JOptionPane.showMessageDialog(this, sb.toString());
}//GEN-LAST:event_aboutBtnActionPerformed

private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
    startBtn.setEnabled(true);                                         
    cancelBtn.setEnabled(false);
    stamp.setStop(true);
}//GEN-LAST:event_cancelBtnActionPerformed

private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
    File srcDir = new File(sourceDir.getText());
    File tgtDir = new File(targetDir.getText());
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("exifphotostamper/messages"); // NOI18N
    
    if (!srcDir.isDirectory() || !srcDir.canRead() || sourceDir.getText().length() == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, sourceDir.getText() + bundle.getString("NotExists"));
        return ;
    }
    
    if (!tgtDir.isDirectory() || targetDir.getText().length() == 0) {
        if(!tgtDir.mkdir()) {
            javax.swing.JOptionPane.showMessageDialog(this, targetDir.getText() + bundle.getString("CannotWrite"));
            return ;
        }
    }
    
    if (sourceDir.getText().equalsIgnoreCase(targetDir.getText())) {
        javax.swing.JOptionPane.showMessageDialog(this, bundle.getString("SourceTargetTheSame"));
        return ;
    }
    
    startBtn.setEnabled(false);
    cancelBtn.setEnabled(true);
    stamp.setStop(false);
    stamp.setSourceDir(sourceDir.getText());
    stamp.setTargetDir(targetDir.getText());
    stamp.setStartDate(startDate.getText());
    stamp.setFontColor(fontColor);
    stamp.setFontBorderColor(fontBorderColor);
    stamp.setFontName((String)fontNameCombo.getSelectedItem());
    stamp.setFontSize(Integer.parseInt(fontSize.getText()));
    stamp.setFormat(format.getText());
    stamp.setMargin(Integer.parseInt(margin.getText()));
    stamp.setQuality(Float.parseFloat(quality.getText()));
    stamp.setCorner(cornerCombo.getSelectedIndex()+1);
    stamp.setShadow(shadowCheckBox.isSelected());
    stamp.setRenameFormat(rename.getText());
    stamp.setRename(renameCheckBox.isSelected());
    storeProperties();
    (new Thread(stamp)).start();
}//GEN-LAST:event_startBtnActionPerformed

    
private void loadProperties() {
        java.io.File propFile = new java.io.File(java.lang.System.getProperty("user.dir"), "setup.properties");
        if (propFile.exists()) {
            try {
                Properties prop = new Properties();
                InputStream is = new FileInputStream(propFile);
                prop.load(is);
                is.close();
                
                startDate.setText((String) prop.get("startDate"));
                sourceDir.setText((String) prop.get("sourceDir"));
                targetDir.setText((String) prop.get("targetDir"));
                fontSize.setText((String) prop.get("fontSize"));
                margin.setText((String) prop.get("margin"));
                format.setText((String) prop.get("format"));
                quality.setText((String) prop.get("quality"));
                fontColor = new Color(Integer.parseInt((String)prop.get("fontColor")));
                fontColorValue.setText("[" + fontColor.getRed() + "," + fontColor.getGreen()+","+ fontColor.getBlue()+"]");
                fontBorderColor = new Color(Integer.parseInt((String)prop.get("fontBorderColor")));
                fontBorderColorValue.setText("[" + fontBorderColor.getRed() + "," + fontBorderColor.getGreen()+","+ fontBorderColor.getBlue()+"]");
                
                int fontNameIdx = ((DefaultComboBoxModel)fontNameCombo.getModel()).getIndexOf((String) prop.get("fontName") );
                fontNameCombo.setSelectedIndex(fontNameIdx);

                cornerCombo.setSelectedIndex(Integer.parseInt((String) prop.get("corner")));
                
                shadowCheckBox.setSelected(Boolean.parseBoolean((String) prop.get("shadow")));
                
            } catch (Exception ex) {
                // ex.printStackTrace();
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            }
        }    
}

private void storeProperties() {
        java.io.File propFile = new java.io.File(java.lang.System.getProperty("user.dir"), "setup.properties");
        Properties prop = new Properties();
        
            try {
                OutputStream os = new FileOutputStream(propFile);
                prop.put("startDate", startDate.getText());
                prop.put("sourceDir", sourceDir.getText());
                prop.put("targetDir", targetDir.getText());
                prop.put("fontName", fontNameCombo.getSelectedItem());
                prop.put("fontSize", fontSize.getText());
                prop.put("fontColor", (Integer.valueOf(fontColor.getRGB())).toString());
                prop.put("fontBorderColor", (Integer.valueOf(fontBorderColor.getRGB())).toString());
                prop.put("margin", margin.getText());
                prop.put("corner", Integer.toString(cornerCombo.getSelectedIndex()));
                prop.put("format", format.getText());
                prop.put("quality", quality.getText());
                prop.put("shadow", Boolean.toString(shadowCheckBox.isSelected()));
                prop.store(os, "ExifPhotoStamper v1.0b");
                os.close();
            } catch (Exception ex) {
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            }        
        
}    

    public void finishAllFiles() {
        startBtn.setEnabled(true);
        cancelBtn.setEnabled(false);
    }
    
    public void finishFileIndex(int index) {
        jProgressBar1.setValue((index+1));
        
        int now = jProgressBar1.getValue();
        int total = jProgressBar1.getMaximum();
        int percent = (int) java.lang.Math.ceil( ((double) now / (double) total) * 100 );
        StringBuffer sb = new StringBuffer();
        sb.append("(" + now + "/" + total +")");
        sb.append(" "+percent+" %");
        jProgressBar1.setString(sb.toString());
        
    }
    
    public void setFilesCount(int count) {
        jProgressBar1.setMaximum(count);
    }
  

private void colorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBtnActionPerformed
    javax.swing.JColorChooser dialog = new JColorChooser();
    fontColor = dialog.showDialog(colorBtn,"", fontColor);
    if (fontColor != null) fontColorValue.setText("[" + fontColor.getRed() + "," + fontColor.getGreen()+","+ fontColor.getBlue()+"]");
}//GEN-LAST:event_colorBtnActionPerformed

private void targetDirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetDirBtnActionPerformed
    JFileChooser dialog = new JFileChooser();
    dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dialog.showOpenDialog(targetDirBtn);
    if (dialog.getSelectedFile() != null) targetDir.setText(dialog.getSelectedFile().toString());
}//GEN-LAST:event_targetDirBtnActionPerformed

private void sourceDirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceDirBtnActionPerformed
    JFileChooser dialog = new JFileChooser();
    dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dialog.showOpenDialog(sourceDirBtn);
    if (dialog.getSelectedFile() != null) sourceDir.setText(dialog.getSelectedFile().toString());
}//GEN-LAST:event_sourceDirBtnActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton colorBorderBtn;
    private javax.swing.JButton colorBtn;
    private javax.swing.JComboBox cornerCombo;
    private javax.swing.JLabel cornerLabel;
    private javax.swing.JLabel fontBorderColorLabel;
    private javax.swing.JTextField fontBorderColorValue;
    private javax.swing.JLabel fontColorLabel;
    private javax.swing.JTextField fontColorValue;
    private javax.swing.JComboBox fontNameCombo;
    private javax.swing.JLabel fontNameLabel;
    private javax.swing.JTextField fontSize;
    private javax.swing.JLabel fontSizeLabel;
    private javax.swing.JTextField format;
    private javax.swing.JLabel formatLabel;
    private javax.swing.JLabel formatLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField margin;
    private javax.swing.JLabel marginLabel;
    private javax.swing.JLabel pixels;
    private javax.swing.JTextField quality;
    private javax.swing.JLabel qualityLabel;
    private javax.swing.JLabel qualityTips;
    private javax.swing.JTextField rename;
    private javax.swing.JCheckBox renameCheckBox;
    private javax.swing.JCheckBox shadowCheckBox;
    private javax.swing.JTextField sourceDir;
    private javax.swing.JButton sourceDirBtn;
    private javax.swing.JLabel sourceDirLabel;
    private javax.swing.JButton startBtn;
    private javax.swing.JTextField startDate;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JTextField targetDir;
    private javax.swing.JButton targetDirBtn;
    private javax.swing.JLabel targetDirLabel;
    // End of variables declaration//GEN-END:variables
    
}
