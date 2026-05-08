-- ======================================================
-- V1 – Schema WellMe: users, trails, phases, quizzes,
--       questions, progress, water_logs, badges, user_badges
-- ======================================================

CREATE TABLE users (
    id              VARCHAR(36)   NOT NULL,
    name            VARCHAR(120)  NOT NULL,
    email           VARCHAR(120)  NOT NULL,
    password_hash   VARCHAR(255)  NOT NULL,
    photo_url       VARCHAR(255),
    xp              INT           NOT NULL DEFAULT 0,
    level           INT           NOT NULL DEFAULT 1,
    daily_water_goal_ml INT       NOT NULL DEFAULT 2000,
    notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP,
    CONSTRAINT pk_users       PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE trails (
    id          VARCHAR(36)  NOT NULL,
    title       VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(50)  NOT NULL, -- HIDRATACAO | ALIMENTACAO | SONO | SAUDE_MENTAL | EXERCICIO | PREVENCAO
    icon_url    VARCHAR(255),
    order_index INT          NOT NULL DEFAULT 0,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_trails PRIMARY KEY (id)
);

CREATE TABLE phases (
    id          VARCHAR(36)  NOT NULL,
    trail_id    VARCHAR(36)  NOT NULL,
    title       VARCHAR(120) NOT NULL,
    content     TEXT         NOT NULL,
    xp_reward   INT          NOT NULL DEFAULT 10,
    order_index INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_phases      PRIMARY KEY (id),
    CONSTRAINT fk_phases_trail FOREIGN KEY (trail_id) REFERENCES trails(id)
);

CREATE TABLE quizzes (
    id          VARCHAR(36)  NOT NULL,
    phase_id    VARCHAR(36)  NOT NULL,
    title       VARCHAR(120) NOT NULL,
    xp_reward   INT          NOT NULL DEFAULT 20,
    CONSTRAINT pk_quizzes       PRIMARY KEY (id),
    CONSTRAINT fk_quizzes_phase FOREIGN KEY (phase_id) REFERENCES phases(id)
);

CREATE TABLE questions (
    id              VARCHAR(36)  NOT NULL,
    quiz_id         VARCHAR(36)  NOT NULL,
    statement       VARCHAR(500) NOT NULL,
    option_a        VARCHAR(255) NOT NULL,
    option_b        VARCHAR(255) NOT NULL,
    option_c        VARCHAR(255) NOT NULL,
    option_d        VARCHAR(255) NOT NULL,
    correct_option  CHAR(1)      NOT NULL, -- A | B | C | D
    order_index     INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_questions       PRIMARY KEY (id),
    CONSTRAINT fk_questions_quiz  FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

CREATE TABLE user_progress (
    id              VARCHAR(36)  NOT NULL,
    user_id         VARCHAR(36)  NOT NULL,
    phase_id        VARCHAR(36)  NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'NOT_STARTED', -- NOT_STARTED | IN_PROGRESS | COMPLETED
    quiz_score      INT,
    xp_earned       INT          NOT NULL DEFAULT 0,
    completed_at    TIMESTAMP,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP,
    CONSTRAINT pk_user_progress        PRIMARY KEY (id),
    CONSTRAINT uq_user_progress_unique UNIQUE (user_id, phase_id),
    CONSTRAINT fk_progress_user        FOREIGN KEY (user_id)  REFERENCES users(id),
    CONSTRAINT fk_progress_phase       FOREIGN KEY (phase_id) REFERENCES phases(id)
);

CREATE TABLE water_logs (
    id              VARCHAR(36)  NOT NULL,
    user_id         VARCHAR(36)  NOT NULL,
    amount_ml       INT          NOT NULL,
    logged_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    log_date        DATE         NOT NULL,
    CONSTRAINT pk_water_logs      PRIMARY KEY (id),
    CONSTRAINT fk_water_logs_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE badges (
    id          VARCHAR(36)  NOT NULL,
    title       VARCHAR(80)  NOT NULL,
    description VARCHAR(255) NOT NULL,
    icon_url    VARCHAR(255),
    criteria    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_badges PRIMARY KEY (id)
);

CREATE TABLE user_badges (
    id          VARCHAR(36) NOT NULL,
    user_id     VARCHAR(36) NOT NULL,
    badge_id    VARCHAR(36) NOT NULL,
    earned_at   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_badges        PRIMARY KEY (id),
    CONSTRAINT uq_user_badge_unique  UNIQUE (user_id, badge_id),
    CONSTRAINT fk_user_badges_user   FOREIGN KEY (user_id)  REFERENCES users(id),
    CONSTRAINT fk_user_badges_badge  FOREIGN KEY (badge_id) REFERENCES badges(id)
);

-- Índices úteis
CREATE INDEX idx_phases_trail           ON phases (trail_id);
CREATE INDEX idx_quizzes_phase          ON quizzes (phase_id);
CREATE INDEX idx_questions_quiz         ON questions (quiz_id);
CREATE INDEX idx_progress_user          ON user_progress (user_id);
CREATE INDEX idx_water_logs_user_date   ON water_logs (user_id, log_date);
CREATE INDEX idx_user_badges_user       ON user_badges (user_id);
