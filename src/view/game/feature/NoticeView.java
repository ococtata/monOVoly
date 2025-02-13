package view.game.feature;

import java.util.ArrayList;
import java.util.List;

import config.DataConfig;
import controller.game.feature.NoticeViewController;
import manager.GameManager;
import utility.FileUtil;
import utility.TextUtil;
import view.BaseView;

public class NoticeView extends BaseView {
	private NoticeViewController noticeViewController;
    
	private int width = WIDTH;
	private int height = HEIGHT;
	
	public NoticeView() {
		this.noticeViewController = new NoticeViewController(this);
		show();	
	}

	@Override
	public void show() {
		TextUtil.clearScreen();
		TextUtil.printHeader("NOTICE", width, height);
		showNoticeDesc();
		System.out.println();
		TextUtil.pressEnter();
		
		if(previousView != null) {
			GameManager.getInstance().setCurrentView(previousView);
            previousView.show();
		}
	}
	
	private void showNoticeDesc() {
		String noticeFolderPath = String.format("%s/notice%d.txt", DataConfig.FOLDER_NOTICES, 
				noticeViewController.getCurrentNoticeIndex());
		
		List<String> notice = FileUtil.readFile(noticeFolderPath);
		printNotice(wrappedLines(notice));
		noticeViewController.increaseNoticeIndex();
	}
	
	private void printNotice(List<String> notice) {
		int linesPerPage = height;
		int totalLines = notice.size();
		int currentLine = 0;
		
		while(currentLine < totalLines) {
			for(int i = 0; i < linesPerPage && currentLine < totalLines; i++) {
				System.out.print(" ");
				System.out.println(notice.get(currentLine));
				currentLine++;
			}
		}
	}
	
	private List<String> wrappedLines(List<String> notice) {
		List<String> wrappedLines = new ArrayList<String>();
		
		for(String line : notice) {
			while(line.length() > width) {
				wrappedLines.add(line.substring(0, width));
				line = line.substring(width);
			}
			wrappedLines.add(line);
		}
		
		return wrappedLines;
	}
}
