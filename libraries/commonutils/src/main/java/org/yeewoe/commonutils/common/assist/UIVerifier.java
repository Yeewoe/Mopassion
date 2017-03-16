package org.yeewoe.commonutils.common.assist;

import android.widget.TextView;

import org.yeewoe.commonutils.common.utils.PatternUtil;

/**
 * UI验证工具
 * Created by wyw on 2016/3/25.
 */
public class UIVerifier {
    public UIVerifier() {

    }

    public boolean verifyEmpty(TextView target) {
        if (target == null || target.getText() == null || Checks.isEmpty(target.getText().toString())) {
            return false;
        }

        return true;
    }

    public boolean verifyLength(TextView target, int min, int max) {
        if (target == null || (min >= 0 && target.length() < min) || (max >= 0 && target.length() > max)) {
            return false;
        }
        return true;
    }

    public boolean verifyEmail(TextView target) {
        if (target == null) {
            return false;
        }

        if (!PatternUtil.isEmail(target.getText().toString())) {
            return false;
        }

        return true;
    }

    public boolean verifyPassword(TextView target) {
        if (target == null) {
            return false;
        }

        if (!PatternUtil.isPassword(target.getText().toString())) {
            return false;
        }

        return true;
    }
}
