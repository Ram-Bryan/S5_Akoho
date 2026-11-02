/*
 * Decompiled with CFR 0.152.
 */
package qrcode.generator;

import java.awt.image.BufferedImage;
import qrcode.generator.GeneratorQrCode;
import qrcode.generator.SimpleGenerator;

public class Passerel {
    private boolean isCrypt = false;
    private String texte = "";
    private GeneratorQrCode g;
    private SimpleGenerator sg;

    public Passerel() {
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(String txt) {
        this.texte = txt;
    }

    public boolean getCrypt() {
        return this.isCrypt;
    }

    public void setCrypt(boolean crypt) {
        this.isCrypt = crypt;
    }

    public Passerel(String text, boolean iscrypt) {
        this.texte = text;
        this.isCrypt = iscrypt;
    }

    public BufferedImage Generate() throws Exception {
        if (!this.texte.equals("")) {
            if (!this.isCrypt) {
                this.sg = new SimpleGenerator(this.texte);
                return this.sg.createImages();
            }
            this.g = new GeneratorQrCode(this.texte);
            return this.g.createImages();
        }
        return null;
    }
}

