package org.outman.dragonfly.rpc.springcloud.fegin;

import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.common.constants.Consts;


/**
 * @ClassName FeignRequestInterceptor
 * @Description TODO
 * @Author OutMan
 * @create 2020-10-22 12:29
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(Consts.DRAGONFLYTOKEN, JSON.toJSONString(TransactionContext.CURRENT_LOCAL.get()));
    }

}
