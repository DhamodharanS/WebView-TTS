package com.example.webpagereader;
//
// enum ESpeak
//{
//  static
//  {
//    eNextSpeak = new ESpeak("eNextSpeak", 1);
//    ESpeak[] arrayOfESpeak = new ESpeak[2];
//    arrayOfESpeak[0] = eStopSpeak;
//    arrayOfESpeak[1] = eNextSpeak;
//  }
//}
//
///* Location:           C:\Users\rajarajan\Desktop\Dhamu\info.bell_pepper-15-v1.14.apk\classes_dex2jar.jar
// * Qualified Name:     info.bell_pepper.ESpeak
// * JD-Core Version:    0.6.2
// */

enum ESpeak {
    E_NEXT_BTN("eNextSpeak", 1),
    E_STOP_BTN("eStopSpeak", 2);
    
    private final String value;
    private final int id;

    private ESpeak(String value, int id) {
        this.value = value;
        this.id = id;
    }

    private final ESpeak[] VALUES = values();
}