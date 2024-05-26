package cn.tw.sar.lightnote.dao;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HitokotoApi {
    /**
     * c	见后表	是	句子类型
     * encode	见后表	是	返回编码
     * charset	见后表	是	字符集
     * callback	如：moe	是	调用的异步函数
     * select	默认：.hitokoto	是	选择器。配合 encode=js 使用
     * min_length	默认：0	是	返回句子的最小长度（包含）
     * max_length	默认：30	是	返回句子的最大长度（包含）
     * @return
     */

    @GET("/")
    Call<HitokotoBean> getHitokoto(

    );

}
