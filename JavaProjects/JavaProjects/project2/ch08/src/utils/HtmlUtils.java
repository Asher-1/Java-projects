package utils;

/**
 * @author lixiaoqing
 *
 */
public class HtmlUtils {
	private static final char[] LT_ENCODE = "&lt;".toCharArray();

	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	private static final char[] BR_TAG = "<BR>".toCharArray();


	/**
	 * 过滤输入表单中的html语句
	 * @param in 输入语句
	 * @return 过滤后的语句
	 */
	public static String escapeHtml(String in){
		if (in == null) {
            return null;
        }
        char ch;
        int i=0;
        int last=0;
        char[] input = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)(len*1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT_ENCODE);
            } else if (ch == '>') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(GT_ENCODE);
            }
        }
        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();

	}
	
	
	/**
	 * 将输入表单中换行符替换成html的<br>
	 * @param input 输入语句
	 * @return 输出语句
	 */
	public static String convertNewlines(String input) {
        char [] chars = input.toCharArray();
        int cur = 0;
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);
        // Loop through each character lookin for newlines.
        for (int i=0; i<len; i++) {
            // If we've found a Unix newline, add BR tag.
            if (chars[i]=='\n') {
                buf.append(chars, cur, i-cur).append(BR_TAG);
                cur = i+1;
            }
            // If we've found a Windows newline, add BR tag.
            else if (chars[i]=='\r' && i<len-1 && chars[i+1]=='\n') {
                buf.append(chars, cur, i-cur).append(BR_TAG);
                i++;
                cur = i+1;
            }
        }
        // Add whatever chars are left to buffer.
        buf.append(chars, cur, len-cur);
        return buf.toString();
    }
	
}
