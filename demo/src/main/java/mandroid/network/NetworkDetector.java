package mandroid.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by KalinaRain on 2015/7/22.
 * 用于检测网络状态
 */
public class NetworkDetector {

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public NetworkDetector(Context context) {
        if (context == null) {
            Log.e("network", "network context is null ");
            return;
        }
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public boolean isAvailable() {

        if (connectivityManager == null) {
            return false;
        }

        if (networkInfo == null || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 获取用户使用的网络类型—— wifi or流量
     * @return 0 流量
     * @return 1 wifi
     * @return 2 其他
     */
    public int getNetType() {
        int a = networkInfo.getType();
        if (a == ConnectivityManager.TYPE_MOBILE) {
            Log.d("a", "用户正在使用-流量-上网");
            return 0;
        } else if (a == ConnectivityManager.TYPE_WIFI) {
            Log.d("a", "用户正在使用-Wifi-上网");
            return 1;
        }
        return 2;
    }

    /**
     * 获取用户的IP地址
     * @return IP
     */
    public String getIp() {
        if (getNetType() == 0) {//用户使用的是流量
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception e) {

            }
        }else if (getNetType() == 1) {//用户使用的是wifi

        }


        return "";
    }
}
