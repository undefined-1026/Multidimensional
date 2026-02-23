package mDimension.entity.bullet;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.Time;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.content.*;
import mindustry.type.StatusEffect;

import java.awt.geom.Point2D;

import static arc.math.Angles.randLenVectors;
import static mindustry.logic.RadarTarget.enemy;

public class BallLightningBulletType extends BasicBulletType {
    // 电弧范围
    public float lightningRange = 60f;
    // 单次电弧伤害
    public float lightningDamage = 20f;
    // 总电击次数
    public int maxStrikes = 5;
    // 电弧冷却时间（帧）
    public float lightningCooldown = 10f;
    // 电弧颜色
    public Color lightningColor = Pal.surge;
    // 一个目标最多的被电击次数
    public int shockLimit = 3;

    public Effect lightningEffect = Fx.chainLightning;

    // 状态效果持续时间
    public float statusDuration = 60f * 2f;
    public StatusEffect lightningStatu = StatusEffects.shocked;

    public Drawer bulletDrawer = b->{};


    public BallLightningBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
    }

    public BallLightningBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public interface Drawer{
        void draw(Bullet b);
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        // 初始化子弹的冷却计时器
        if (b.data == null) {
            b.data = 0f; // 存储为 Float
        }
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        shock(b);
    }

    public void shock(Bullet b){
        if (b.data == null) {
            b.data = 0f;
        }

        // 获取并更新冷却计时器
        float lightningTimer = ((Number)b.data).floatValue();
        lightningTimer += Time.delta;

        // 每过冷却时间，检测周围敌人并释放电弧
        if (lightningTimer >= lightningCooldown) {
            lightningTimer = 0f;

            // 收集范围内的所有有效敌人
            Seq<Healthc> targets = new Seq<>();

            Units.nearbyEnemies(b.team, b.x, b.y, lightningRange, enemy -> {
                if (!enemy.dead() && enemy.isValid() && enemy.targetable(b.team)) {
                    targets.add(enemy);
                }
            });
            Units.nearbyBuildings(b.x, b.y, lightningRange, build -> {
                if (build.team != b.team && build.isValid() && build.block.targetable) {
                    targets.add(build);
                }
            });
            int remainingStrikes = maxStrikes;
            // 如果有敌人，循环电击直到消耗完所有次数
            if (targets.size > 0) {
                ObjectMap<Healthc,Integer> shocksMap = new ObjectMap<>();
                for(Healthc u:targets){
                    shocksMap.put(u,0);
                }
                // 随机生成初始索引
                int currentIndex = Mathf.random(targets.size - 1);

                // 循环电击敌人，直到用完所有次数
                while (remainingStrikes > 0 && targets.size > 0) {
                    // 确保索引在有效范围内
                    currentIndex %= targets.size;
                    if (currentIndex < 0) currentIndex = 0;

                    Healthc target = targets.get(currentIndex);
                    // 获取当前敌人
                    if(targets.get(currentIndex) instanceof Unit Utarget){
                        if (Utarget.dead() || !Utarget.isValid() || !Utarget.targetable(b.team) || shocksMap.get(Utarget)>=shockLimit) {
                            // 如果敌人无效，移除它
                            targets.remove(currentIndex);
                            shocksMap.remove(Utarget);
                            if (targets.size == 0) break;
                            // 保持索引不变，因为下一个敌人会移动到当前位置\\
                            // 施加状态
                            continue;
                        }
                        if(lightningStatu!=null&&lightningStatu!=StatusEffects.none) {
                            Utarget.apply(lightningStatu, statusDuration);
                        }
                    }else if(targets.get(currentIndex) instanceof Building Btarget){
                        if (Btarget.dead() || !Btarget.isValid()|| shocksMap.get(Btarget)>=shockLimit) {
                            // 如果敌人无效，移除它
                            targets.remove(currentIndex);
                            shocksMap.remove(Btarget);
                            if (targets.size == 0) break;
                            // 保持索引不变，因为下一个敌人会移动到当前位置
                            continue;
                        }
                    }

                    // 检查敌人是否仍然有效
                    Vec2 endPoint = new Vec2(b.x,b.y);
                    // 绘制电弧
                    lightningEffect.at(target.getX(),target.getY(), 0f, lightningColor,endPoint);
                    // 造成伤害
                    target.damage(lightningDamage * b.damageMultiplier());
                    shocksMap.put(target,shocksMap.get(target)+1);

                    // 减少剩余次数
                    remainingStrikes--;

                    // 移动到下一个敌人，循环处理
                    currentIndex++;
                }
            }
            remaining(b,remainingStrikes);

            // 保存冷却计时器
            b.data = lightningTimer;
        } else {
            // 保存冷却计时器
            b.data = lightningTimer;
        }
    }
    /// 仅视觉效果
    public void remaining(Bullet b,int count){
        count = Math.min(count, 6);
        if(count <=0 || !b.isAdded())return;
        Vec2 endPoint = new Vec2(b.x,b.y);
        Angles.randLenVectors((long) (Time.time+b.id* 114L),count,lightningRange*0.25f,lightningRange*0.7f,(x, y)->{
            lightningEffect.at(b.getX()+x,b.getY()+y, 0f, lightningColor,endPoint);
        });
    }

    public void draw(Bullet b){
        super.draw(b);
        bulletDrawer.draw(b);
    }
}