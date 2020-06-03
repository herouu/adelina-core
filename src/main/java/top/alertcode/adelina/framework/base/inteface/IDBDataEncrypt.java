package top.alertcode.adelina.framework.base.inteface;

/**

 * 创建时间：2020/4/9
 * 功能描述: 数据加密／解密接口
 * <p>
 * 修订记录：
 * @version 1.0
 **/
public interface IDBDataEncrypt {

    /***
     * 数据加密密钥Key（对称加密密钥）
     * @return
     */
    String getEncryptKey();


    /***
     * 对字符传进行加密
     * @param encryptString 需要加密的字符传
     * @return 密文
     */
    String encrypt(String encryptString);

    /***
     * 对字符传进行解密
     * @param decrypt 密文
     * @return
     */
    String decrypt(String decrypt);
}
