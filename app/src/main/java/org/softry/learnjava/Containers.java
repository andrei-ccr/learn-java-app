package org.softry.learnjava;

import android.content.Context;
import android.util.Log;

public class Containers {

	public static class LessonContent {
		private String[] content, pageTitle;
		private int[] pageRead;
		
		LessonContent(String[] content, String[] pageTitle, int[] pageRead) {
			this.content = content;
			this.pageTitle = pageTitle;
			this.pageRead = pageRead; 
		}

		public LessonContent(Context context, Integer[] contentRIDs, Integer[] pageTitleRIDs, int[] pageRead){
		    String[] content = new String[contentRIDs.length];
		    String[] pageTitle = new String[pageTitleRIDs.length];
		    for(int i=0;i<contentRIDs.length;i++) {
                content[i] = context.getResources().getString(contentRIDs[i]);
                pageTitle[i] = context.getResources().getString(pageTitleRIDs[i]);
            }

            this.content = content;
            this.pageTitle = pageTitle;
            this.pageRead = pageRead;

        }

		
		String GetContentByPage(int page) {
			try {
				return this.content[page];
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("myapp", "Exception when trying to print content from page " + Integer.toString(page));
				return "";
			}
		}
		
		String GetTitleByPage(int page) {
			try {
				return this.pageTitle[page];
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("myapp", "Exception when trying to print page title from page " + Integer.toString(page));
				return "";
			}
		}
		
		int GetReadStatusByPage(int page) {
			try {
				return this.pageRead[page];
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("myapp", "Exception when trying to print page read status from page " + Integer.toString(page));
				return -1;
			}
		}
		
		String[] GetAllContent() {
			return this.content;
		}
		
		int[] GetAllReadStatus() {
			return this.pageRead;
		}
		
		int GetPageCount() {
			return this.pageRead.length;
		}
		
		int GetReadCount() {
			int k = 0;
			for(Integer i : this.pageRead) {
				if(i == 1) k++;
			}
			return k;
		}

		void MarkPageAsRead(int page) {
			this.pageRead[page] = 1;
		}
	}
	
	static class Lesson {
		private String title, desc;
		private int imgResId;
		private LessonContent lessonContent;
		private int completedPercent; //From 0 to 100
        private boolean locked;
        private boolean comingSoon = false;
		
		Lesson(String title, String desc, int imgResId, LessonContent lessonContent) {
			this.title = title;
			this.desc = desc;
			this.imgResId = imgResId;
			this.lessonContent = lessonContent;
			this.locked = true;
			
			this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
		}

		Lesson(boolean comingSoon, String title, String desc, int imgResId, LessonContent lessonContent) {
			this(title,desc,imgResId,lessonContent);
			this.comingSoon = comingSoon;
			this.locked = false;
			this.completedPercent = 100;
		}

		boolean ComingSoon() {
			return this.comingSoon;
		}
		
		private void SetCompletedPercent(int val) {
			this.completedPercent = Math.max(0, Math.min(100, val));
		}

		void UnlockLesson() {
		    this.locked = false;
        }
        void LockLesson() {
		    this.locked = true;
        }

        boolean IsLocked() {
		    return this.locked;
        }
		
		String GetTitle() {
			return this.title;
		}
		
		String GetDesc() {
			return this.desc;
		}
		
		int GetImageRID() {
			return this.imgResId;
		}
		
		int GetCompletedPercent(Context context) {
            this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
            if(this.completedPercent == 100) {
                Utilities.UnlockNextLesson(context);
            }
            return this.completedPercent;
		}

		boolean IsCompleted() {
            this.SetCompletedPercent((int)Math.floor(((float)this.lessonContent.GetReadCount()/(float)this.lessonContent.GetPageCount())*100f));
            return this.completedPercent == 100;
        }
		
		LessonContent GetLessonContent() {
			return this.lessonContent;
		}

		void ResetCompletedPercent(Context context) {
			LessonContent currentLC = this.lessonContent;
			this.lessonContent = new LessonContent(currentLC.content, currentLC.pageTitle, new int[currentLC.content.length]);
            GetCompletedPercent(context);
		}

	}
	
    public static class Chapter {
        private String number;
        private String name;
        private String longDesc;
        private String shortDesc;
        private int progress;
        private Integer[] lessonList;
        private int lessonCount;

        public Chapter(String number, String name, String shortDesc, String longDesc, Integer[] lessonList) {
            this.number = number;
            this.name = name;
            this.shortDesc = shortDesc;
            this.longDesc = longDesc;
            this.progress = -1; // -1 means not started
            this.lessonList = lessonList;
            if(lessonList != null)
                this.lessonCount = lessonList.length;
        }

        private void UpdateProgress(Context context) {
            double total_lprogress = 0;
            int comingSoonLessons = 0;
            try {
				for(Integer l : this.lessonList) {
				    if(Utilities.LessonList.get(l).ComingSoon()) {
				        comingSoonLessons++;
				        continue;
                    }
					double lprogress = Utilities.LessonList.get(l).GetCompletedPercent(context) / 100f;
					total_lprogress += lprogress;
				}
			} catch (Exception e) {
            	this.progress = -1;
            	return;
			}
            if(total_lprogress == 0) this.progress = -1;
            else
                this.progress = (int)((total_lprogress/(this.lessonCount-comingSoonLessons))*100);
        }

        /*public void SetProgress(int value) {
            if(value<0) this.progress = -1;
            else if(value>100) this.progress = 100;
            else this.progress = value;
        }*/

        //public int GetLessonCount() {return this.lessonCount;}
        String GetNumber(){
            return this.number;
        }

        String GetName() {
            return this.name;
        }

        String GetShortDesc() {
            return this.shortDesc;
        }
        String GetLongDesc() {
            return this.longDesc;
        }

        String GetProgressStr(Context context) {
            if(GetProgressValue(context) == -1) {
                return "";
            } else if(GetProgressValue(context) >= 100) {
               return "Completed";
            } else {
                return Integer.toString(GetProgressValue(context)) + "%";
            }
        }

        int GetProgressValue(Context context) {
            UpdateProgress(context);
            return this.progress;
        }
    }
}
