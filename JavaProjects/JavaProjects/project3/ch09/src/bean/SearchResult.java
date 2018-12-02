package bean;

import java.util.ArrayList;

public class SearchResult<T> {
	private int status ;
	protected ArrayList<T> record ;
	
	public SearchResult(ArrayList<T> records,int status) {
		super();
		this.record = records;
		this.status = status;
	}
	public SearchResult() {
		this.record = new ArrayList<T>();
		this.status = SearchStatus.SUCCESS.ordinal();
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<T> getRecord() {
		return record;
	}
	public void setRecord(ArrayList<T> record) {
		this.record = record;
	}
}
