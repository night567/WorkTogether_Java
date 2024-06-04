package cn.edu.szu.user.service.impl;

import cn.edu.szu.user.service.impl.COSClientUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 腾讯云COS文件上传工具类
 */
@Slf4j
public class COSClientUtil {

    public static String sendToTencentCOS(MultipartFile multipartFile) throws IOException {
        // 1 初始化用户身份信息（secretId, secretKey）。
// SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDa2HH4lkV2KOVkrjVD9uRlpd5KRJvJWWK";//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        String secretKey = "kIenkT1PhpncIoJepuqYClFexCNVWQBm";//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println(bucketName);
            System.out.println(bucketLocation);
        }
//         指定要上传的文件
        File localFile = File.createTempFile("temp-file-", ".tmp");
        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(localFile));
// 指定文件将要存放的存储桶
        String bucketName = "worktogether-336-1322238572";
        String uuid = UUID.randomUUID().toString();
// 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = "folder/"+uuid+".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//        System.out.println(putObjectResult);
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
// 详细代码参见本页：创建 COSClient


// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
//        String bucketName = "worktogether-336-1322238572";
// 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
//        String key = "folder/"+uuid;

// 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
// 这里设置签名在1000个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 1000);

// 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("param1", "value1");

// 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("header1", "value1");

// 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;

        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        System.out.println(url.toString());

// 确认本进程不再使用 cosClient 实例之后，关闭即可
        cosClient.shutdown();
        return url.toString();
    }

 
}