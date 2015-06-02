package com.example.lenovo.homework;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class UpdateManager
{
	private static final int DOWNLOAD = 1;
	private static final int DOWNLOAD_FINISH = 2;
	HashMap<String, String> mHashMap;
	private String mSavePath;
	private int progress;
	private boolean cancelUpdate = false;

	private Context mContext;
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case DOWNLOAD:
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context)
	{
		this.mContext = context;
	}

	public void checkUpdate()
	{
		if (isUpdate())
		{
			Toast.makeText(mContext,"客官请稍等",Toast.LENGTH_LONG).show();
			showNoticeDialog();
		} else
		{
			//本来这里应该设置为不需要更新，为了展示出来更新的效果，所以还是进行下载
			Toast.makeText(mContext,"客官请稍等",Toast.LENGTH_LONG).show();
			showNoticeDialog();
			//Toast.makeText(mContext,"并不需要更新", Toast.LENGTH_LONG).show();
		}
	}

	InputStream inStream = PraseXmlService.class.getClassLoader().getResourceAsStream("http://hongyan.cqupt.edu.cn/app/cyxbsAppUpdate.xml");

	private boolean isUpdate()
	{
		int versionCode = getVersionCode(mContext);

		PraseXmlService service = new PraseXmlService();
		try
		{
			mHashMap = service.parseXml(inStream);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (null != mHashMap)
		{
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			// 版本判断
			if (serviceCode > versionCode)
			{
				return true;
			}
		}
		return false;
	}

	private int getVersionCode(Context context)
	{
		int versionCode = 0;
		try
		{
			versionCode = context.getPackageManager().getPackageInfo("com.szy.update", 0).versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}

	private void showNoticeDialog()
	{
		// 构造对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		PraseXmlService service = new PraseXmlService();
		try {
			mHashMap = service.parseXml(inStream);
			String soft_update_info = mHashMap.get("updateContent");
			builder.setMessage(soft_update_info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 更新
		builder.setPositiveButton("立即更新", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 显示下载对话框
				showDownloadDialog();
			}
		});
		// 稍后更新
		builder.setNegativeButton("稍后更新", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog()
	{
		Builder builder = new Builder(mContext);
		builder.setTitle("正在更新");
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		builder.setNegativeButton("取消更新", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
//		mDownloadDialog.show();
		downloadApk();
	}

	private void downloadApk()
	{
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					String sdpath = Environment.getExternalStorageDirectory() + "/";
					mSavePath = sdpath + "download";
					URL url = new URL("http://hongyan.cqupt.edu.cn/app/com.mredrock.cyxbs.apk");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					if (!file.exists())
					{
						file.mkdir();
					}//mSavePath, mHashMap.get("versionName")
					File apkFile = new File(mSavePath, "update_file");
					Log.d("UpdateManager","11111111111");
					FileOutputStream fos = new FileOutputStream(apkFile);
					Log.d("UpdateManager","222222222222");
					int count = 0;
					byte buf[] = new byte[1024];
					Log.d("UpdateManager","33333333333");
					do
					{
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);
						Log.d("UpdateManager","4444444444");
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							Log.d("UpdateManager", "55555555555");
							break;
						}
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			mDownloadDialog.dismiss();
		}
	};

	private void installApk()
	{
		File apkfile = new File(mSavePath, "update_file");
		if (!apkfile.exists())
		{
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
