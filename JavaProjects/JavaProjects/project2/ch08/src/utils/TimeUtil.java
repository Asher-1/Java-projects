package utils;

public class TimeUtil {
	private long lastAdd;

	public TimeUtil(long lastAdd) {
		this.lastAdd = lastAdd;
	}

	public long getLastAdd() {
		return lastAdd;
	}

	public void setLastAdd(long lastAdd) {
		this.lastAdd = lastAdd;
	}
}
