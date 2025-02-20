package controller.game.feature;

import java.io.File;
import java.util.List;

import config.DataConfig;
import manager.GameManager;
import utility.FileUtil;
import utility.Random;
import view.game.feature.NoticeView;

public class NoticeViewController implements Random{
	private NoticeView noticeView;
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
	public static int getMaxNoticeIndex() {
		return maxNoticeIndex;
	}
	
	public int randomIndex() {
		int newIndex = rand.nextInt(maxNoticeIndex) + 1;
		return newIndex;
	}
	
	public void showNoticeDesc() {
		String noticeFolderPath = String.format("%s/notice%d.txt", DataConfig.FOLDER_NOTICES, 
				randomIndex());
		
		List<String> notice = FileUtil.readFile(noticeFolderPath);
		noticeView.printNotice(noticeView.wrappedLines(notice));
	}
}
