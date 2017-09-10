package customized;

public class Time {
	public static long timeFromMark(long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	public static String msToString(long ms) {
		int time = (int) (ms / 1000L);
		int h = time / 3600;
		int m = time / 60 % 60;
		int s = time % 60;
		return (h < 10 ? "0" + h : Integer.valueOf(h)) + ":" + (m < 10 ? "0" + m : Integer.valueOf(m)) + ":"
				+ (s < 10 ? "0" + s : Integer.valueOf(s));
	}
}
