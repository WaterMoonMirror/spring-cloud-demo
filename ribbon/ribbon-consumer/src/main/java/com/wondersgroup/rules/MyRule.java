package com.wondersgroup.rules;

import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.sun.javafx.binding.StringFormatter;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.Rule;
import org.apache.tomcat.util.digester.Rules;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2021/1/25 10:26
 * @description: 自定义rule
 */
public class MyRule extends AbstractLoadBalancerRule implements Rules {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String url=request.getServletPath()+"?"+request.getQueryString();

        return route(url.hashCode(),getLoadBalancer().getAllServers());
    }

    /**
     *   环形负载均衡
     * @param hashId
     * @param addressList
     * @return
     */
    public Server route(int hashId, List<Server> addressList) {
        if (CollectionUtils.isEmpty(addressList)) {
            return null;
        }

        TreeMap<Long, Server> address = new TreeMap<>();
        addressList.stream().forEach(e -> {
            // 虚化若干个服务节点，到环上
            for (int i = 0; i < 8; i++) {
                long hash = hash(e.getId() + i);
                address.put(hash, e);
            }
        });

        long hash = hash(String.valueOf(hashId));
        SortedMap<Long, Server> last = address.tailMap(hash);
        // 当request URL的hash值大于任意一个服务器对应的hashKey，
        // 取address中的第一个节点
        if (last.isEmpty()) {
            address.firstEntry().getValue();
        }

        return last.get(last.firstKey());
    }

    public long hash(String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] keyByte = null;
        try {
            keyByte = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        md5.update(keyByte);
        byte[] digest = md5.digest();

        long hashCode = ((long) (digest[2] & 0xFF << 16))
                | ((long) (digest[1] & 0xFF << 8))
                | ((long) (digest[0] & 0xFF));

        return hashCode & 0xffffffffL;
    }


    @Override
    public Digester getDigester() {
        return null;
    }

    @Override
    public void setDigester(Digester digester) {

    }

    @Override
    public void add(String s, Rule rule) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<Rule> match(String s, String s1) {
        return null;
    }

    @Override
    public List<Rule> rules() {
        return null;
    }
    final  static AtomicInteger atomicInteger=new AtomicInteger();
    public static void main(String[] args) {
        Executor executor=Executors.newFixedThreadPool(10);
        for (int i = 0; i <10 ; i++) {
        executor.execute(()-> System.out.println("线程->"+atomicInteger.incrementAndGet()+""));
        }

    }
}
