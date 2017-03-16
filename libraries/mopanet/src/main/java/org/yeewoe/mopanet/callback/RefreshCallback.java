package org.yeewoe.mopanet.callback;

import java.util.List;

public interface RefreshCallback {
    public <T> void callback(CallbackInfo<T> info);

    public enum RefreshType {
        LOCALE,
        NET
    }

    public static class CallbackInfo<T> {
        public RefreshType type;
        public T mT;
        public List<T> mTs;

        public boolean bError = false;
        public int errorCode;
    }
}
