/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.zxing.BarcodeFormat
 *  com.google.zxing.WriterException
 *  com.google.zxing.common.BitMatrix
 *  com.google.zxing.qrcode.QRCodeWriter
 */
package qrcode.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import configuration.CynthiaConf;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SimpleGenerator
extends JPanel {
    private String text = "";
    private BufferedImage image;
    Properties prop = CynthiaConf.load();
    private String qrcode = this.prop.getProperty("cdnQrCode");

    public String getQrcode() {
        return this.qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public SimpleGenerator(String text) throws Exception {
        this.text = text;
        this.setSize(300, 300);
        try {
            this.image = ImageIO.read(new File(this.qrcode + "logo.jpg"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public BitMatrix generationQR(String data, int size) throws WriterException {
        BitMatrix b = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size);
        return b;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BitMatrix bM = null;
        try {
            bM = this.generationQR(this.text, 300);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        this.setBackground(Color.WHITE);
        g.setColor(Color.BLACK);
        for (int i = 0; i < bM.getWidth(); ++i) {
            for (int j = 0; j < bM.getHeight(); ++j) {
                if (!bM.get(i, j)) continue;
                g.fillRect(j, i, 1, 1);
            }
        }
    }

    public void createImage(String path) {
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        Graphics2D g = bi.createGraphics();
        this.paint(g);
        File outputfile = new File(path);
        try {
            ImageIO.write((RenderedImage)bi, "png", outputfile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage createImages() {
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        Graphics2D g = bi.createGraphics();
        this.paint(g);
        return bi;
    }
}

