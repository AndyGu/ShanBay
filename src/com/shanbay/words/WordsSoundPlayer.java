package com.shanbay.words;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.File;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

public class WordsSoundPlayer
{
  private View mAudioButton;
  private WordsClient mClient;
  private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener()
  {
    public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
    {
      if (WordsSoundPlayer.this.mAudioButton != null)
        WordsSoundPlayer.this.mAudioButton.setSelected(false);
      if (WordsSoundPlayer.this.mPlayer != null)
      {
        WordsSoundPlayer.this.mPlayer.release();
        WordsSoundPlayer.this.mPlayer = null;
      }
    }
  };
  private MediaPlayer mPlayer = new MediaPlayer();

  public WordsSoundPlayer()
  {
    this.mPlayer.setOnCompletionListener(this.mOnCompletionListener);
  }

  public WordsSoundPlayer(WordsClient paramWordsClient)
  {
    this();
    this.mClient = paramWordsClient;
  }

  // ERROR //
  private void play(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   4: ifnull +10 -> 14
    //   7: aload_0
    //   8: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   11: invokevirtual 57	android/media/MediaPlayer:release	()V
    //   14: aload_0
    //   15: new 25	android/media/MediaPlayer
    //   18: dup
    //   19: invokespecial 26	android/media/MediaPlayer:<init>	()V
    //   22: putfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   25: aload_0
    //   26: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   29: aload_0
    //   30: getfield 23	com/shanbay/words/WordsSoundPlayer:mOnCompletionListener	Landroid/media/MediaPlayer$OnCompletionListener;
    //   33: invokevirtual 32	android/media/MediaPlayer:setOnCompletionListener	(Landroid/media/MediaPlayer$OnCompletionListener;)V
    //   36: aconst_null
    //   37: astore_2
    //   38: new 59	java/io/FileInputStream
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 61	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   46: astore_3
    //   47: aload_0
    //   48: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   51: invokevirtual 64	android/media/MediaPlayer:reset	()V
    //   54: aload_0
    //   55: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   58: aload_3
    //   59: invokevirtual 68	java/io/FileInputStream:getFD	()Ljava/io/FileDescriptor;
    //   62: invokevirtual 72	android/media/MediaPlayer:setDataSource	(Ljava/io/FileDescriptor;)V
    //   65: aload_0
    //   66: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   69: invokevirtual 75	android/media/MediaPlayer:prepare	()V
    //   72: aload_0
    //   73: getfield 28	com/shanbay/words/WordsSoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   76: invokevirtual 78	android/media/MediaPlayer:start	()V
    //   79: aload_3
    //   80: invokestatic 84	com/shanbay/util/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   83: return
    //   84: astore 4
    //   86: new 86	java/io/File
    //   89: dup
    //   90: aload_1
    //   91: invokespecial 87	java/io/File:<init>	(Ljava/lang/String;)V
    //   94: astore 5
    //   96: aload 5
    //   98: invokevirtual 91	java/io/File:exists	()Z
    //   101: ifeq +9 -> 110
    //   104: aload 5
    //   106: invokevirtual 94	java/io/File:delete	()Z
    //   109: pop
    //   110: aload 4
    //   112: invokevirtual 97	java/io/IOException:printStackTrace	()V
    //   115: aload_2
    //   116: invokestatic 84	com/shanbay/util/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   119: return
    //   120: astore 8
    //   122: aload 8
    //   124: invokevirtual 98	java/lang/IllegalStateException:printStackTrace	()V
    //   127: aload_2
    //   128: invokestatic 84	com/shanbay/util/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   131: return
    //   132: astore 6
    //   134: aload_2
    //   135: invokestatic 84	com/shanbay/util/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
    //   138: aload 6
    //   140: athrow
    //   141: astore 6
    //   143: aload_3
    //   144: astore_2
    //   145: goto -11 -> 134
    //   148: astore 8
    //   150: aload_3
    //   151: astore_2
    //   152: goto -30 -> 122
    //   155: astore 4
    //   157: aload_3
    //   158: astore_2
    //   159: goto -73 -> 86
    //
    // Exception table:
    //   from	to	target	type
    //   38	47	84	java/io/IOException
    //   38	47	120	java/lang/IllegalStateException
    //   38	47	132	finally
    //   86	110	132	finally
    //   110	115	132	finally
    //   122	127	132	finally
    //   47	79	141	finally
    //   47	79	148	java/lang/IllegalStateException
    //   47	79	155	java/io/IOException
  }

  public void playSound(String paramString, View paramView)
  {
    if (StringUtils.isBlank(paramString));
    while (!Env.getAudioCacheFile(paramString).exists())
      return;
    if (this.mAudioButton != null)
    {
      this.mAudioButton.setSelected(false);
      this.mAudioButton = null;
    }
    if (paramView != null)
    {
      this.mAudioButton = paramView;
      this.mAudioButton.setSelected(true);
    }
    play(Env.getAudioCacheFile(paramString).getAbsolutePath());
  }

  public void playSoundByUrl(String paramString, View paramView)
  {
    if (this.mAudioButton != null)
    {
      this.mAudioButton.setSelected(false);
      this.mAudioButton = null;
    }
    if (paramView != null)
    {
      this.mAudioButton = paramView;
      this.mAudioButton.setSelected(true);
    }
    this.mClient.cacheSound(paramString, new AsyncHttpResponseHandler()
    {
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
        if (WordsSoundPlayer.this.mAudioButton != null)
          WordsSoundPlayer.this.mAudioButton.setSelected(false);
		
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		WordsSoundPlayer.this.play(new String(arg2));
		
	}
    });
  }

  public void stopSound()
  {
    if (this.mAudioButton != null)
      this.mAudioButton.setSelected(false);
    if (this.mPlayer != null)
    {
      this.mPlayer.stop();
      this.mPlayer.release();
      this.mPlayer = null;
    }
  }
}