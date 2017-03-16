package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.Toast;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.emotion.emoji.EmojiJsonParser;


public class ChatEditText extends EditText {

    public static final String TAG = "ChatEditText";

    public ChatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return super.onCreateInputConnection(outAttrs);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onTextContextMenuItem(int id) {
        switch (id) {
            case android.R.id.paste:
                try {
                    ClipboardManager cmb1 = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cmb1.getText() != null) {
                        String str = EmojiJsonParser.parseEmoji(cmb1.getText().toString());
                        append(EmotionUtil.parseToMsg(str, getContext(), false));
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case android.R.id.copy:
                ClipboardManager cmb2 = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                try {
                    int start = getSelectionStart();
                    int end = getSelectionEnd();
                    cmb2.setText(EmotionUtil.parseToText(getText().subSequence(start, end).toString(), getContext()));
                } catch (Exception e) {
                    return false;
                }

                return true;
            default:
                break;
        }
        try {
            return super.onTextContextMenuItem(id);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_copy_or_paste_fail, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

//	public class DeleteLisInputConnection extends BaseInputConnection {
//
//		public DeleteLisInputConnection(View targetView, boolean fullEditor) {
//			super(targetView, fullEditor);
//		}
//		
//		@Override
//		public boolean sendKeyEvent(KeyEvent event) {
//			Log.i(TAG, "sendKeyEvent");
//			if(event.getAction() == KeyEvent.ACTION_DOWN &&
//					event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
//				return false;
//			}
//				
//			return super.sendKeyEvent(event);
//		}
//		
//		@Override
//		public boolean deleteSurroundingText(int beforeLength, int afterLength) {
//			
////			if(beforeLength > afterLength) {
////				return super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
////						&& super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
////			}
//			
//			return super.deleteSurroundingText(beforeLength, afterLength);
//			
//		}
//		
//	}

}
