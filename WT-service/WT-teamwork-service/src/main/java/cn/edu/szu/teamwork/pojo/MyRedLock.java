package cn.edu.szu.teamwork.pojo;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;

import java.util.List;

/**
 * RedLock重写，避免3个redis时，一个redis节点崩溃无法进行加锁的情况
 */
public class MyRedLock extends RedissonRedLock {
    public MyRedLock(RLock... locks) {
        super(locks);
    }
    @Override
    protected int minLocksAmount(List<RLock> locks) {
        return 1;
    }
}
