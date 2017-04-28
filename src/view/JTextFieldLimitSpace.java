package view;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class JTextFieldLimitSpace extends JTextField {
    private int limit;

    public JTextFieldLimitSpace(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {

        @Override
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
            if (str == null) 
            	return;

            if ((getLength() + str.length()) <= limit) {
				//String newstr = str.replaceAll(" ", "");
            	 super.insertString(offset, str, attr);
            }
        }
        
       @Override
        public void replace(int offset, int len, String str, AttributeSet attr) throws BadLocationException {
          //  String newstr = str.replaceAll(" ", "");      			// could use "\\s" instead of " "
            super.replace(offset, len, str, attr);
        }

    }

}
