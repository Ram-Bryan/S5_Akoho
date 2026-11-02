/*
 * Decompiled with CFR 0.152.
 */
package importUtils.inputStreamTranslater;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public interface InputStreamTranslater {
    public List<HashMap<String, String>> translate(InputStream var1, String[] var2, boolean var3) throws Exception;
}

