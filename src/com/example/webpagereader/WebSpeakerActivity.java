package com.example.webpagereader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class WebSpeakerActivity extends Activity
  implements View.OnClickListener, View.OnTouchListener, TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener, DialogInterface.OnDismissListener
{
	
	Button play,stop;
  static final int ADD_BOOKMARK_DLG = 6;
  private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAitqSVGrYCqBYE/a+heo7FyL9SY1/lIe1jAcJwC/3S453WEFfuC1vJfMQoRIXbirR2YMGfvvlL62XKTN9CVG7v6F+0ydJyF841DutTzTbg+NMJNZgQFQHrmnH42eZvtl72X9yuxWe4blhcnxd2aUk0lK/j+0dznpcG56wnJdAi1NH7aLl8sLuy8iIjRlQu/ITCzB83bj4TUwatVkKfpRC+BoYWMnor/ILPXjQgYvsnO4COHN1dyRgz06AUS5Kacs5y8yEKshLk2ZQBXfqqfc7GPClorlEE7WHVEc0F0aw+wfkPlYf0CF/UlOpHukalpSjn4nMIfCfQv/XzRbXp4Sg6wIDAQAB";
  static final int DEL_BOOKMARK_DLG = 7;
  static final int LICENSE_DLG = 3;
  static final int MSG_DLG = 1;
  private static final int MY_DATA_CHECK_CODE = 1;
  private static final byte[] SALT = { -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64, 89 };
  static final int SPEAKER_DLG = 2;
  static final int SPEED_DLG = 4;
  static final int VIEW_BOOKMARK_DLG = 5;
  static final int VIEW_HISTORY_DLG = 8;
  WebSpeakerActivity mActivity;
  //private AdView mAdView;
  EditText mAddress;
  //private LicenseChecker mChecker = null;
  String mCmd;
  Locale mCurLocale = null;
  boolean mDelayInitTts;
  ImageButton mGo;
  Handler mHandler;
  /*ArrayAdapter<THistory> mHistory;
  HashMap<String, THistory> mHistoryMap = new HashMap();
  */boolean mInit;
  //private LicenseCheckerCallback mLicenseCheckerCallback;
  String mLog;
  Menu mMenu;
  String mMsg;
  ImageButton mNext;
  ImageButton mPlay;
  MediaPlayer mPlayer;
  ImageButton mPrev;
  boolean mReadPhrase;
  boolean mSetLang;
  ESpeak mSpeak;
  int mSpeakFlag = 0;
  String mStr;
  TextToSpeech mTts;
  String mTtsLang;
  String mUrl;
  PowerManager.WakeLock mWake;
  WebView mWebView;
  HashMap<String, String> myHashAlarm = new HashMap();
  ArrayList<String> vISOLang;
  ArrayList<Locale> vLocale;
  static int playflag=0;

  public static void Print(String paramString)
  {
  }

  private void displayResult(final String paramString)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        WebSpeakerActivity.this.mMsg = paramString;
        WebSpeakerActivity.this.showDialog(1);
      }
    });
  }

 /* private void doCheck()
  {
    setProgressBarIndeterminateVisibility(true);
    this.mChecker.checkAccess(this.mLicenseCheckerCallback);
  }*/

  public void AAA()
  {
  }

  void DoCmd(String paramString)
  {
    this.mWebView.loadUrl("javascript:(typeof(gBellPepperInfo) == 'undefined' ? bell_pepper_info.InitRun('gBellPepperInfo." + paramString + "') : gBellPepperInfo." + paramString + " )");
  }

  /*void DspBtn(EBtnSts paramEBtnSts)
  {
    boolean bool1;
    label61: boolean bool2;
    label89: ImageButton localImageButton3;
    boolean bool3;
    switch ($SWITCH_TABLE$info$bell_pepper$EBtnSts()[paramEBtnSts.ordinal()])
    {
    default:
      ImageButton localImageButton1 = this.mPrev;
      if ((paramEBtnSts != EBtnSts.eStopBtn) && (paramEBtnSts != EBtnSts.ePrevBtn))
      {
        bool1 = false;
        localImageButton1.setEnabled(bool1);
        ImageButton localImageButton2 = this.mPlay;
        if ((paramEBtnSts == EBtnSts.eStopBtn) || (paramEBtnSts == EBtnSts.ePlayBtn))
          break label256;
        bool2 = false;
        localImageButton2.setEnabled(bool2);
        localImageButton3 = this.mNext;
        if (paramEBtnSts == EBtnSts.eStopBtn)
          break label262;
        EBtnSts localEBtnSts = EBtnSts.eNextBtn;
        bool3 = false;
        if (paramEBtnSts == localEBtnSts)
          break label262;
      }
      break;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      localImageButton3.setEnabled(bool3);
      return;
      this.mPrev.setImageResource(2130837511);
      this.mPlay.setImageResource(2130837509);
      this.mNext.setImageResource(2130837506);
      break;
      this.mPrev.setImageResource(2130837512);
      this.mPlay.setImageResource(2130837508);
      this.mNext.setImageResource(2130837507);
      break;
      this.mPrev.setImageResource(2130837511);
      this.mPlay.setImageResource(2130837510);
      this.mNext.setImageResource(2130837507);
      break;
      this.mPrev.setImageResource(2130837512);
      this.mPlay.setImageResource(2130837510);
      this.mNext.setImageResource(2130837506);
      break;
      bool1 = true;
      break label61;
      label256: bool2 = true;
      break label89;
      label262: bool3 = true;
    }
  }*/

  void Go()
  {
    //String str1 = this.mAddress.getText().toString().trim();
    String str1 = "file:///android_asset/" + "index.html";
    if (str1.indexOf("://") == -1)
    {
      String[] arrayOfString = str1.split(" ");
      
      if ((arrayOfString.length != 0) && (((arrayOfString[0].length() != 0) && (arrayOfString[0].charAt(0) == '"')) || (arrayOfString[0].indexOf('.') == -1)))
        try
        {
          str1 = URLEncoder.encode(str1, "utf-8");
          String str2 = "http://www.google.com/search?q=" + str1;
          this.mWebView.loadUrl(str2);
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
        }
      str1 = "http://" + str1;
    }
    this.mWebView.loadUrl(str1);
  }

  String HelpURL()
  {
    String str = Locale.getDefault().getLanguage();
   /* if (str.equals("ja"))
      return "file:///android_asset/" + "index.html";
    	//return "http://www.google.co.in";
    if (str.equals("ko"))
    	//return "http://www.google.co.in";
      return "file:///android_asset/" + "index.html";*/
    return "file:///android_asset/" + "index.html";
   // return "http://www.google.co.in";
  }

/*  void InitCheckLicense()
  {
    String str = Settings.Secure.getString(getContentResolver(), "android_id");
    this.mLicenseCheckerCallback = new MyLicenseCheckerCallback(null);
    this.mChecker = new LicenseChecker(this, new ServerManagedPolicy(this, new AESObfuscator(SALT, getPackageName(), str)), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAitqSVGrYCqBYE/a+heo7FyL9SY1/lIe1jAcJwC/3S453WEFfuC1vJfMQoRIXbirR2YMGfvvlL62XKTN9CVG7v6F+0ydJyF841DutTzTbg+NMJNZgQFQHrmnH42eZvtl72X9yuxWe4blhcnxd2aUk0lK/j+0dznpcG56wnJdAi1NH7aLl8sLuy8iIjRlQu/ITCzB83bj4TUwatVkKfpRC+BoYWMnor/ILPXjQgYvsnO4COHN1dyRgz06AUS5Kacs5y8yEKshLk2ZQBXfqqfc7GPClorlEE7WHVEc0F0aw+wfkPlYf0CF/UlOpHukalpSjn4nMIfCfQv/XzRbXp4Sg6wIDAQAB");
  }*/

  public void InitRun(String paramString)
  {
    this.mCmd = paramString;
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        WebSpeakerActivity.this.mWebView.loadUrl(TScript.Src + WebSpeakerActivity.this.mCmd);
      }
    });
  }

  /*void InitSelectSpeakerDlg(TSpeakerDlg paramTSpeakerDlg)
  {
    if (this.mSetLang);
    while (true)
    {
      return;
      this.mSetLang = true;
      paramTSpeakerDlg.rgrSpeaker = ((RadioGroup)paramTSpeakerDlg.findViewById(2131165196));
      paramTSpeakerDlg.rgrSpeaker.removeAllViews();
      for (int i = 0; i < this.vLocale.size(); i++)
      {
        Locale localLocale = (Locale)this.vLocale.get(i);
        Print("Lang EQ:" + localLocale.equals(this.mCurLocale) + " " + localLocale.getDisplayLanguage());
        RadioButton localRadioButton = new RadioButton(this);
        localRadioButton.setText(localLocale.getDisplayLanguage());
        localRadioButton.setOnClickListener(paramTSpeakerDlg);
        localRadioButton.setChecked(localLocale.equals(this.mCurLocale));
        paramTSpeakerDlg.rgrSpeaker.addView(localRadioButton);
      }
    }
  }*/

  void InitTts()
  {
    if (this.mDelayInitTts)
      return;
   // this.mSpeak = ESpeak.eStopSpeak;
    this.mTts = new TextToSpeech(this, this);
  }

  /*public void LoadHistory()
  {
    if (this.mHistory != null);
    String str;
    do
    {
      return;
      this.mHistory = new ArrayAdapter(this, 2130903041);
      this.mHistoryMap = new HashMap();
      str = getPreferences(0).getString("History", "");
    }
    while (str.length() == 0);
    String[] arrayOfString1 = str.split("\n");
    int i = 0;
    while (i < arrayOfString1.length)
    {
      String[] arrayOfString2 = arrayOfString1[i].split("\t");
      if (arrayOfString2.length == 3);
      try
      {
        long l = 60000L * Long.parseLong(arrayOfString2[0], 16);
        this.mHistory.add(new THistory(this.mHistoryMap, new Date(l), arrayOfString2[1], arrayOfString2[2]));
        label142: i++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        break label142;
      }
    }
  }*/

  public void Log(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(this.mLog));
    if (this.mLog == "");
    for (String str = ""; ; str = "\r\n")
    {
      this.mLog = (str + paramString);
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          if (WebSpeakerActivity.this.mLog != "")
          {
            WebSpeakerActivity.Print(WebSpeakerActivity.this.mLog);
            WebSpeakerActivity.this.mLog = "";
          }
        }
      });
      return;
    }
  }

  void MakeLangList()
  {
    String str = getPreferences(0).getString("SpeakerLang", "");
    Print("GET SpeakerLang:" + str);
    Locale localLocale1 = null;
    if (str != "")
      localLocale1 = new Locale(str);
    this.mCurLocale = null;
    Print("TTSの言語のリストを作る");
    String[] arrayOfString = Locale.getISOLanguages();
    this.vISOLang = new ArrayList();
    this.vLocale = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString.length)
        return;
      Locale localLocale2 = new Locale(arrayOfString[i]);
      if (this.mTts.isLanguageAvailable(localLocale2) >= 0)
      {
        this.vLocale.add(localLocale2);
        this.vISOLang.add(arrayOfString[i]);
        if (localLocale2.equals(localLocale1))
          this.mCurLocale = localLocale1;
      }
    }
  }

  public void OpenMarket(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(Uri.parse("market://details?id=" + paramString));
    try
    {
      startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      new AlertDialog.Builder(this.mActivity).setMessage("Android Market is not available.").create().show();
    }
  }

  void Pause()
  {
    StopSpeak();
    DoCmd("StopTimer()");
  }

  void Play()
  {
   /* DspBtn(EBtnSts.ePlayBtn);
    this.mSpeak = ESpeak.eNextSpeak;*/
	  if(playflag!=1)
	  {
        DoCmd("SpeakCur()");
	  }
  }

 public boolean ReadPhrase()
  {
    return this.mReadPhrase;
  }

 /* public void SaveHistory()
  {
    if (this.mHistory == null)
      return;
    StringWriter localStringWriter = new StringWriter(65536);
    for (int i = 0; ; i++)
    {
      if (i >= this.mHistory.getCount());
      do
      {
        SharedPreferences.Editor localEditor = getPreferences(0).edit();
        localEditor.putString("History", localStringWriter.toString());
        localEditor.commit();
        return;
        THistory localTHistory = (THistory)this.mHistory.getItem(i);
        localStringWriter.append(Long.toHexString(localTHistory.DateHis.getTime() / 60000L));
        localStringWriter.append('\t');
        localStringWriter.append(localTHistory.TitleHis);
        localStringWriter.append('\t');
        localStringWriter.append(localTHistory.UrlHis);
        localStringWriter.append('\n');
      }
      while (1048576 < localStringWriter.toString().length());
    }
  }*/

  void SelectSpeaker(boolean paramBoolean)
  {
    MakeLangList();
    if (this.mCurLocale != null)
    {
      this.mTts.setLanguage(this.mCurLocale);
      this.mTtsLang = this.mCurLocale.getLanguage();
      this.mReadPhrase = this.mTtsLang.equals("ja");
      Print("既定の言語゛:" + this.mReadPhrase + " " + this.mCurLocale.getLanguage() + " " + this.mCurLocale.getDisplayLanguage());
      if (!paramBoolean)
        return;
    }
    if (this.vLocale.size() == 0)
    {
      Intent localIntent = new Intent();
      localIntent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
      startActivity(localIntent);
      return;
    }
    this.mSetLang = false;
    showDialog(2);
  }

  /*void SetHomepage()
  {
    new AlertDialog.Builder(this).setMessage(2131034134).setCancelable(true).setPositiveButton(2131034147, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        SharedPreferences.Editor localEditor = WebSpeakerActivity.this.getPreferences(0).edit();
        localEditor.putString("HomePage", WebSpeakerActivity.this.mWebView.getUrl());
        localEditor.commit();
        new AlertDialog.Builder(WebSpeakerActivity.this.mActivity).setTitle(2131034133).setMessage(2131034135).show();
        paramAnonymousDialogInterface.dismiss();
      }
    }).setNegativeButton(2131034148, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    }).create().show();
  }*/

  public void SetSpeed(int paramInt)
  {
    if (paramInt < 5);
    for (float f = 0.5F + 0.5F * paramInt / 5.0F; ; f = 1.0F + (paramInt - 5) / 5.0F)
    {
      Print("速度の設定:" + (int)(100.0F * f));
      this.mTts.setSpeechRate(f);
      return;
    }
  }

  /*void ShowAd()
  {
    this.mAdView = new AdView(this, AdSize.BANNER, "a14f142f792ad82");
    ((LinearLayout)findViewById(2131165187)).addView(this.mAdView, 1);
    AdRequest localAdRequest = new AdRequest();
    localAdRequest.addTestDevice(AdRequest.TEST_EMULATOR);
    this.mAdView.loadAd(localAdRequest);
  }*/

  void ShowHelp()
  {
    this.mWebView.loadUrl(HelpURL());
  }

  void ShowInitMsg()
  {
	  
    SharedPreferences localSharedPreferences = getPreferences(0);
    if (localSharedPreferences.getInt("ShowInitMsg", 0) == 0)
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putInt("ShowInitMsg", 1);
      localEditor.commit();
    }
    WebSpeakerActivity.this.mDelayInitTts = false;
    WebSpeakerActivity.this.InitTts();
      /*if (localSharedPreferences.getString("History", "").length() != 0)
        break label106;*/
     /* new AlertDialog.Builder(this).setMessage(2131034144).setCancelable(false).setPositiveButton(2131034147, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          WebSpeakerActivity.this.mDelayInitTts = false;
          WebSpeakerActivity.this.InitTts();
          paramAnonymousDialogInterface.dismiss();
        }
      }).create().show();
      this.mDelayInitTts = true;
    }
    label106: 
    while (Locale.getDefault().getLanguage().equals("ja"))
      return;
    new AlertDialog.Builder(this).setMessage(2131034145).setCancelable(false).setPositiveButton(2131034146, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        WebSpeakerActivity.this.mWebView.loadUrl(WebSpeakerActivity.this.HelpURL() + "#paid_ver");
        WebSpeakerActivity.this.mDelayInitTts = false;
        WebSpeakerActivity.this.InitTts();
        paramAnonymousDialogInterface.dismiss();
      }
    }).setNegativeButton(2131034148, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        WebSpeakerActivity.this.mDelayInitTts = false;
        WebSpeakerActivity.this.InitTts();
        paramAnonymousDialogInterface.cancel();
      }
    }).create().show();
    this.mDelayInitTts = true;*/
  }

  public void Speak(int paramInt, String paramString)
  {
    this.mSpeakFlag = paramInt;
    this.mStr = paramString;
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        WebSpeakerActivity.Print("speak: " + WebSpeakerActivity.this.mStr);
        if (WebSpeakerActivity.this.mTts != null)
        {
          if (WebSpeakerActivity.this.mTts.isSpeaking())
            WebSpeakerActivity.this.mTts.stop();
          WebSpeakerActivity.this.mTts.speak(WebSpeakerActivity.this.mStr, 0, WebSpeakerActivity.this.myHashAlarm);
        }
        WebSpeakerActivity.this.mStr = "";
      }
    });
  }

  void StopSpeak()
  {
    //this.mSpeak = ESpeak.eStopSpeak;
    if (this.mTts != null)
      this.mTts.stop();
   // DspBtn(EBtnSts.eStopBtn);
  }

  public String Str(int paramInt)
  {
    return getResources().getString(paramInt);
  }

  public String TtsLang()
  {
    return this.mTtsLang;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramInt2 == 1))
    {
      Print("TTSは使用可能");
      InitTts();
    }
  }

  public void onClick(View paramView)
  {
   // Print("click:" + paramView.getId() + " " + 2131165191);
    switch (paramView.getId())
    {
   /* case 2131165189:
    case 2131165190:
    default:
      return;*/
    case R.id.button1:
    	//WebSpeakerActivity.this.Play();
    	if (this.mTts == null)
    	      InitTts();
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          /*if (WebSpeakerActivity.this.mSpeak != ESpeak.E_STOP_BTN)
          {
            WebSpeakerActivity.this.Pause();
            return;
          }*/
          WebSpeakerActivity.this.Play();
          playflag=1;
        }
      });
     // Go();
      return;
    case R.id.button2:
    	if (this.mTts != null)
        {
    	  mSpeakFlag=1;
    	  playflag=0;
    	  DoCmd("StopTimer()");
          this.mTts.stop();
          this.mTts.shutdown();
          this.mTts = null;
        }
    return;
    	//StopSpeak();
        /*this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (WebSpeakerActivity.this.mSpeak != ESpeak.E_STOP_BTN)
            {
              WebSpeakerActivity.this.Pause();
              return;
            }
           // WebSpeakerActivity.this.Pause();
        	  
          }
        });
        return;*/
   // case 2131165188:
    }
  //  Go();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mInit = false;
    this.mActivity = this;
    this.mReadPhrase = false;
    this.mTtsLang = "";
    this.mDelayInitTts = false;
    this.mHandler = new Handler();
    requestWindowFeature(2);
    setContentView(R.layout.main);
    this.mActivity.setTitle("WEB");
    this.mStr = "";
    this.myHashAlarm.put("utteranceId", "my id");
    this.play = ((Button)findViewById(R.id.button1));
    this.play.setOnClickListener(this);
    this.stop = ((Button)findViewById(R.id.button2));
    this.stop.setOnClickListener(this);
   /* this.mPlayer = MediaPlayer.create(this, 2130968576);
    this.mAddress = ((EditText)findViewById(2131165185));
    this.mGo = ((ImageButton)findViewById(2131165188));
    this.mGo.setImageResource(2130837504);
    this.mGo.setOnClickListener(this);*/
    this.mWebView = ((WebView)findViewById(R.id.webview));
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    this.mWebView.addJavascriptInterface(this, "bell_pepper_info");
    this.mWebView.requestFocus();
    this.mWebView.setWebChromeClient(new WebChromeClient()
    {
    });
    this.mWebView.setWebViewClient(new HelloWebViewClient());
    this.mWebView.getSettings().setBuiltInZoomControls(true);
    /*Intent localIntent = getIntent();
    String str = null;
    if (localIntent != null)
    {
      boolean bool = localIntent.getAction().equals("android.intent.action.SEND");
      str = null;
      if (bool)
        str = localIntent.getStringExtra("android.intent.extra.TEXT");
    }
    if ((str != null) && (str.indexOf("://") != -1))
      this.mWebView.loadUrl(str);*/
    
    
   /*while (true)
    {
     // this.mWebView.requestFocus();
      this.mWebView.setWebChromeClient(new WebChromeClient()
      {
        public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
        {
          WebSpeakerActivity.this.mActivity.setTitle("Loading...");
          WebSpeakerActivity.this.mActivity.setProgress(paramAnonymousInt * 100);
          if (paramAnonymousInt == 100)
            WebSpeakerActivity.this.mActivity.setTitle(2131034113);
        }
      });*/
      
    
     /* this.mWebView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(final View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
        	mHandler.post(new Runnable()
            {
              public void run()
              {
            	
                Play();
               
              }
            });
           // Go();
        //	onClick(paramAnonymousView);
            
          switch (paramAnonymousMotionEvent.getAction())
          {
          default:
          case 0:
          case 1:
          }
          while (true)
          {
            
            if (!paramAnonymousView.hasFocus())
              paramAnonymousView.requestFocus();
            return false;
          }
        }
      });
      this.mWebView.setOnLongClickListener(new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramAnonymousView)
        {
          WebSpeakerActivity.Print("long click");
          return true;
        }
      });*/
      /*this.mPrev = ((ImageButton)findViewById(2131165190));
      this.mPrev.setImageResource(2130837511);
      this.mPrev.setOnClickListener(this);
      this.mPrev.setOnTouchListener(this);
      this.mPlay = ((ImageButton)findViewById(2131165191));
      this.mPlay.setImageResource(2130837509);
      this.mPlay.setOnClickListener(this);
      this.mNext = ((ImageButton)findViewById(2131165192));
      this.mNext.setImageResource(2130837506);
      this.mNext.setOnClickListener(this);
      this.mNext.setOnTouchListener(this);
      this.mWake = ((PowerManager)getSystemService("power")).newWakeLock(6, "disableLock");
      this.mWake.acquire();*/
      ShowInitMsg();
      InitTts();
     /* ShowAd();
      LoadHistory();
      */
      this.mWebView.loadUrl(getPreferences(0).getString("HomePage", HelpURL()));
      
      
      
     return;
      
      
      
      
   // }
  }

  /*protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 2:
      Print("言語の選択のダイアログの作成");
      TSpeakerDlg localTSpeakerDlg = new TSpeakerDlg(this.mActivity);
      localTSpeakerDlg.setContentView(2130903043);
      localTSpeakerDlg.setTitle(2131034124);
      InitSelectSpeakerDlg(localTSpeakerDlg);
      return localTSpeakerDlg;
    case 4:
      TSpeedDlg localTSpeedDlg = new TSpeedDlg(this.mActivity);
      localTSpeedDlg.InitSpeedDlg();
      return localTSpeedDlg;
    case 1:
      return new AlertDialog.Builder(this).setMessage(this.mMsg).create();
    case 3:
      return new AlertDialog.Builder(this).setTitle(2131034119).setMessage(2131034120).setPositiveButton(2131034121, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://market.android.com/details?id=" + WebSpeakerActivity.this.getPackageName()));
          WebSpeakerActivity.this.startActivity(localIntent);
        }
      }).setNegativeButton(2131034122, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          WebSpeakerActivity.this.finish();
        }
      }).create();
    case 5:
      return new TViewBookmarkDlg(this);
    case 8:
      return new TViewHistoryDlg(this);
    case 6:
      return new TAddBookmarkDlg(this);
    case 7:
    }
    return new TDelBookmarkDlg(this);
  }*/

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    this.mMenu = paramMenu;
    getMenuInflater().inflate(2131099648, paramMenu);
    return true;
  }

 /* protected void onDestroy()
  {
    super.onDestroy();
    this.mWake.release();
    if (this.mChecker != null)
      this.mChecker.onDestroy();
  }*/

 /* public void onDismiss(DialogInterface paramDialogInterface)
  {
    if ((paramDialogInterface instanceof TSpeakerDlg))
      removeDialog(2);
    do
    {
      return;
      if ((paramDialogInterface instanceof TSpeedDlg))
      {
        removeDialog(4);
        return;
      }
      if ((paramDialogInterface instanceof TViewBookmarkDlg))
      {
        removeDialog(5);
        return;
      }
      if ((paramDialogInterface instanceof TViewHistoryDlg))
      {
        removeDialog(8);
        return;
      }
      if ((paramDialogInterface instanceof TAddBookmarkDlg))
      {
        removeDialog(6);
        return;
      }
    }
    while (!(paramDialogInterface instanceof TDelBookmarkDlg));
    removeDialog(7);
  }*/

  public void onInit(int paramInt)
  {
    if (this.mTts == null)
      return;
    Toast.makeText(getApplicationContext(), "Calling", Toast.LENGTH_LONG).show();
    if(this.mTts != null)
    {
    //SelectSpeaker(false);
    this.mTts.setOnUtteranceCompletedListener(this);
    SetSpeed(getPreferences(0).getInt("Speed", 5));
    }
    
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    /*switch (paramInt)
    {
    default:
    case 82:
    case 4:
    }*/
    do
    //  while (true)
      {
        
        Pause();
        playflag=0;
        return super.onKeyDown(paramInt, paramKeyEvent);
      }while (!this.mWebView.canGoBack());
    //this.mWebView.goBack();
   // return true;
  }

  /*public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 2131165203:
    case 2131165204:
    case 2131165205:
    case 2131165206:
    case 2131165212:
    case 2131165209:
    case 2131165208:
    case 2131165210:
    case 2131165211:
    case 2131165207:
    }
    while (true)
    {
      return super.onOptionsItemSelected(paramMenuItem);
      Pause();
      SelectSpeaker(true);
      return true;
      Pause();
      showDialog(5);
      continue;
      Pause();
      LoadHistory();
      showDialog(8);
      continue;
      this.mWebView.reload();
      continue;
      SetHomepage();
      continue;
      Pause();
      showDialog(4);
      continue;
      ShowHelp();
      continue;
      Pause();
      showDialog(6);
      continue;
      Pause();
      showDialog(7);
      continue;
      try
      {
        this.mWebView.stopLoading();
      }
      catch (Exception localException)
      {
      }
    }
  }

  protected void onPause()
  {
    super.onPause();
    this.mSpeak = ESpeak.eStopSpeak;
    if (this.mTts != null)
    {
      this.mTts.stop();
      this.mTts.shutdown();
      this.mTts = null;
    }
    DoCmd("StopTimer()");
    SaveHistory();
  }

  protected void onPrepareDialog(int paramInt, Dialog paramDialog, Bundle paramBundle)
  {
    switch (paramInt)
    {
    case 3:
    case 4:
    default:
      return;
    case 2:
      Print("言語の選択のダイアログの再利用");
      InitSelectSpeakerDlg((TSpeakerDlg)paramDialog);
      return;
    case 5:
      ((TViewBookmarkDlg)paramDialog).InitDlg();
      return;
    case 8:
      ((TViewHistoryDlg)paramDialog).InitDlg();
      return;
    case 6:
      ((TAddBookmarkDlg)paramDialog).InitDlg();
      return;
    case 7:
    }
    ((TDelBookmarkDlg)paramDialog).InitDlg();
  }

  protected void onResume()
  {
    super.onResume();
    Print("on Resume");
    this.mHistory = null;
    LoadHistory();
    DspBtn(EBtnSts.eStopBtn);
    if (this.mTts == null)
      InitTts();
  }
*/
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    String str = "";
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
     // Print("touch:" + str + " view:" + paramView.getId() + " next:" + 2131165192);
     
      str = "ACTION_DOWN";
      
      switch (paramView.getId())
      {
      case R.id.button1:
      default:
        break;
     /* case 2131165190:
        str = str + ":prev";
        StopSpeak();
        //DspBtn(EBtnSts.ePrevBtn);
        DoCmd("Rewind()");
        break;
      case 2131165192:
        str = str + "next";
        StopSpeak();
       // DspBtn(EBtnSts.eNextBtn);
        DoCmd("FastForward()");
       /* continue;
        str = "ACTION_UP";*/
        /*switch (paramView.getId())
        {
        case R.id.button1:
        default:
          break;
        case 2131165190:
        case 2131165192:
         // DspBtn(EBtnSts.eStopBtn);
          DoCmd("StopTimer()");
          continue;
          str = "ACTION_MOVE";
          continue;
          str = "ACTION_CANCEL";
        }*/
        
      }
      return false;
    }
  }

  public void onUtteranceCompleted(String paramString)
  {
    if (!paramString.equals("my id"))
      return;
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (WebSpeakerActivity.this.mSpeakFlag == 1)
        {
          WebSpeakerActivity.this.Pause();
          return;
        }
        /*switch ($SWITCH_TABLE$info$bell_pepper$ESpeak()[WebSpeakerActivity.this.mSpeak.ordinal()])
        {
        case 1:
        default:
          return;
        case 2:
        }*/
        WebSpeakerActivity.Print("音声終了:Speak Next");
        WebSpeakerActivity.this.DoCmd("SpeakNext()");
      }
    });
  }

  private class HelloWebViewClient extends WebViewClient
  {
    private HelloWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      WebSpeakerActivity.Print("読み込み終了");
      WebSpeakerActivity.this.mUrl = paramString;
      WebSpeakerActivity.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          WebSpeakerActivity.this.DoCmd("PageFinished()");
         // WebSpeakerActivity.this.mAddress.setText(WebSpeakerActivity.this.mUrl);
         /* if (WebSpeakerActivity.this.mHistory != null)
          {
            WebBackForwardList localWebBackForwardList = WebSpeakerActivity.this.mWebView.copyBackForwardList();
            int i = localWebBackForwardList.getSize();
            if (i != 0)
            {
              WebHistoryItem localWebHistoryItem = localWebBackForwardList.getItemAtIndex(i - 1);
              String str1 = localWebHistoryItem.getUrl();
              if (!WebSpeakerActivity.this.mHistoryMap.containsKey(str1))
              {
                String str2 = localWebHistoryItem.getTitle();
                if ((str2 == null) || (str2.length() == 0))
                  str2 = str1;
                WebSpeakerActivity.this.mHistory.insert(new THistory(WebSpeakerActivity.this.mHistoryMap, new Date(), str2, str1), 0);
              }
            }
          }*/
          if (!WebSpeakerActivity.this.mInit)
            WebSpeakerActivity.this.mInit = true;
        }
      });
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      WebSpeakerActivity.Print("読み込み開始");
      WebSpeakerActivity.this.mUrl = paramString;
      WebSpeakerActivity.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          WebSpeakerActivity.this.StopSpeak();
          WebSpeakerActivity.this.DoCmd("StartPage()");
         // WebSpeakerActivity.this.mAddress.setText(WebSpeakerActivity.this.mUrl);
          if (WebSpeakerActivity.this.mTts != null)
            WebSpeakerActivity.this.mTts.stop();
        }
      });
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if ((paramString != null) && (paramString.equals("about:blank")))
        return false;
      paramWebView.loadUrl(paramString);
      return true;
    }
  }

@Override
public void onDismiss(DialogInterface dialog) {
	// TODO Auto-generated method stub
	
}

  /*private class MyLicenseCheckerCallback
    implements LicenseCheckerCallback
  {
    private MyLicenseCheckerCallback()
    {
    }

    public void allow()
    {
      if (WebSpeakerActivity.this.isFinishing())
        return;
      WebSpeakerActivity.Print("ライセンス:許可");
    }

    public void applicationError(LicenseCheckerCallback.ApplicationErrorCode paramApplicationErrorCode)
    {
      if (WebSpeakerActivity.this.isFinishing())
        return;
      String str = String.format(WebSpeakerActivity.this.getString(2131034118), new Object[] { paramApplicationErrorCode });
      WebSpeakerActivity.this.displayResult(str);
    }

    public void dontAllow()
    {
      if (WebSpeakerActivity.this.isFinishing())
        return;
      WebSpeakerActivity.this.displayResult(WebSpeakerActivity.this.getString(2131034116));
      WebSpeakerActivity.this.showDialog(3);
    }
  }*/
}

/* Location:           C:\Users\rajarajan\Desktop\Dhamu\info.bell_pepper-15-v1.14.apk\classes_dex2jar.jar
 * Qualified Name:     info.bell_pepper.WebSpeakerActivity
 * JD-Core Version:    0.6.2
 */