package controller.game.feature;

import java.io.File;

import config.DataConfig;
import view.game.feature.NoticeView;

public class NoticeViewController {
	private NoticeView noticeView;
	private static int currentNoticeIndex = 1;
	private static int maxNoticeIndex;
	
	public NoticeViewController(NoticeView noticeView) {
		this.noticeView = noticeView;
		maxNoticeIndex = getNoticeFileCount();
	}
	
	public int getNoticeFileCount() {
		File folder = new File(DataConfig.FOLDER_NOTICES);
        File[] files = folder.listFiles();
        
        return files.length;
	}

	public int getCurrentNoticeIndex() {
		return currentNoticeIndex;
	}

	public static int getMaxNoticeIndex() {
		return maxNoticeIndex;
	}
	
	public int increaseNoticeIndex() {
		int newIndex = currentNoticeIndex + 1;
		if(newIndex > maxNoticeIndex) {
			return 1;
		}
		return newIndex;
	}
}
