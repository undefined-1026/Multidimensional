package mDimension.tool;

import arc.struct.Seq;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;

import java.lang.reflect.Field;

public class StatusAccessor {
    // 反射字段
    private static Field statusesField;

    static {
        try {
            // 获取 StatusComp 类的 class 对象
            Class<?> statusCompClass = Class.forName("mindustry.entities.comp.StatusComp");
            // 获取 statuses 字段
            statusesField = statusCompClass.getDeclaredField("statuses");
            // 设置字段为可访问（突破私有访问限制）
            statusesField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单位的状态数组
     */
    @SuppressWarnings("unchecked")
    public static Seq<StatusEntry> getStatuses(Unit unit) {
        try {
            // 获取单位的 StatusComp 实例并返回其 statuses 字段值
            return (Seq<StatusEntry>) statusesField.get(unit);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
