-- ============================================================
-- 数据库初始化脚本 - cloud_db
-- ============================================================
CREATE DATABASE IF NOT EXISTS cloud_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE cloud_db;

-- ---------- 用户表 ----------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username    VARCHAR(64)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码',
    nickname    VARCHAR(64)           DEFAULT NULL COMMENT '昵称',
    email       VARCHAR(128)          DEFAULT NULL COMMENT '邮箱',
    phone       VARCHAR(32)           DEFAULT NULL COMMENT '手机号',
    balance     DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '余额',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删 1-已删',
    create_time DATETIME              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ---------- 订单表 ----------
DROP TABLE IF EXISTS biz_order;
CREATE TABLE biz_order (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    order_no    VARCHAR(64)   NOT NULL COMMENT '订单号',
    user_id     BIGINT        NOT NULL COMMENT '用户ID',
    amount      DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    status      TINYINT       NOT NULL DEFAULT 0 COMMENT '状态：0-待支付 1-已支付 2-已取消 3-已完成',
    remark      VARCHAR(255)           DEFAULT NULL COMMENT '备注',
    create_time DATETIME               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ---------- Seata AT 模式 undo_log 表（每个业务库都需要） ----------
DROP TABLE IF EXISTS undo_log;
CREATE TABLE undo_log (
    branch_id     BIGINT       NOT NULL COMMENT 'branch transaction id',
    xid           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    context       VARCHAR(128) NOT NULL COMMENT 'undo_log context',
    rollback_info LONGBLOB     NOT NULL COMMENT 'rollback info',
    log_status    INT          NOT NULL COMMENT '0:undo 1:defense',
    log_created   DATETIME     NOT NULL COMMENT 'create datetime',
    log_modified  DATETIME     NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY ux_undo_log (xid, branch_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Seata undo log';

-- ---------- 初始化数据 ----------
INSERT INTO sys_user (username, password, nickname, email, phone, balance, status) VALUES
('admin', '123456', '管理员', 'admin@cloud.com', '13800000000', 10000.00, 1),
('user01', '123456', '测试用户', 'user01@cloud.com', '13800000001', 500.00, 1);
