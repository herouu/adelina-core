package top.alertcode.adelina.framework.utils;

import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * <p>IpHostUtils class.</p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public class IpHostUtils {

    private static volatile String cachedIpAddress;

    private IpHostUtils() {
    }

    /**
     * <p>getHostName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getHostName() {
        return getLocalHost().getHostName();
    }

    /**
     * <p>getIp.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getIp() {
        if (StringUtils.isNotBlank(cachedIpAddress)) {
            return cachedIpAddress;
        }

        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            String localIpAddress = null;
            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();

                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress ipAddress = inetAddressEnumeration.nextElement();

                    if (isPublicIpAddress(ipAddress)) {
                        String hostAddress = ipAddress.getHostAddress();
                        cachedIpAddress = hostAddress;
                        return hostAddress;
                    }

                    if (isLocalIpAddress(ipAddress)) {
                        localIpAddress = ipAddress.getHostAddress();
                        cachedIpAddress = localIpAddress;
                    }
                }
            }
            return localIpAddress;
        } catch (final SocketException ex) {
            throw new FrameworkUtilException(ex);
        }
    }

    private static boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isV6IpAddress(final InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }

    private static InetAddress getLocalHost() {
        InetAddress result;
        try {
            result = InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            throw new FrameworkUtilException(ex);
        }
        return result;
    }
}
