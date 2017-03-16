package org.yeewoe.mopanet.callback;

import org.yeewoe.mopanet.client.MopaErrno;

import java.util.List;

public interface Callback {
    public <T> void callback(CallbackInfo<T> info);

    public static class CallbackInfo<T> {
        public T mT;
        public List<T> mTs;

        public boolean bError = false;
        public int errorCode;

        @Override
        public String toString() {
            MopaErrno mopaErrno = MopaErrno.parse(errorCode);
            return "CallbackInfo{" +
                    " bError=" + bError +
                    ", errorCode=" + errorCode +
                    ", errorName=" + (mopaErrno != null ? mopaErrno.name() : null) +
                    ", mT=" + mT +
                    ", mTs=" + mTs +
                    '}';
        }
    }

}
