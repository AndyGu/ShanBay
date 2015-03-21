package com.shanbay.words.model;

public class ReviewStat {
	private int failure;
	private int init;
	private int recognition;
	private int success;
	private int total;

	public void clear() {
		this.total = 0;
		this.init = 0;
		this.success = 0;
		this.failure = 0;
		this.recognition = 0;
	}

	public void decLevelByReviewStatus(int paramInt) {
		switch (paramInt) {
		case 0:
			if (init > 0)
				init = init - 1;
			return;
		case 2:
			if (recognition > 0)
				recognition = recognition - 1;
			return;
		case 1:
			if (success > 0)
				success = success - 1;
			return;
		case 3:
			if (failure > 0)
				failure = failure - 1;
			return;
		default:
			return;
		}
	}

	public ReviewStat getData() {
		ReviewStat localReviewStat = new ReviewStat();
		localReviewStat.total = total;
		localReviewStat.failure = failure;
		localReviewStat.init = init;
		localReviewStat.success = success;
		localReviewStat.recognition = recognition;
		return localReviewStat;
	}

	public int getFailure() {
		return failure;
	}

	public int getInit() {
		return init;
	}

	public int getRecognition() {
		return recognition;
	}

	public int getSuccess() {
		return success;
	}

	public int getTotal() {
		return total;
	}

	public void incLevelByReviewStatus(int paramInt) {
		switch (paramInt) {
		case 0:
			if (init < total) {
				init = 1 + init;
			} else {
				init = total;
			}
			return;

		case 1:
			if (success < total) {
				success = 1 + success;
			} else {
				success = total;
			}
			return;
		case 2:
			if (recognition < total) {
				recognition = 1 + recognition;
			} else {
				recognition = total;
			}
			return;
		case 3:
			if (failure < total) {
				failure = 1 + failure;
			} else {

				failure = total;
			}
			return;

		default:
			return;
		}
	}

	public void setTotal(int mTotal) {
		total = mTotal;
	}
}