package loadgeneration;

public class GenerateLoadForm {
	private Integer numThreads;
	private Integer loopCount;

	public GenerateLoadForm() {
		this.numThreads = 2;
		this.loopCount = 400000;
	}

	public Integer getNumThreads() {
		return numThreads;
	}
	public void setNumThreads(Integer numThreads) {
		this.numThreads = numThreads;
	}
	public Integer getLoopCount() {
		return loopCount;
	}
	public void setLoopCount(Integer loopCount) {
		this.loopCount = loopCount;
	}
}
