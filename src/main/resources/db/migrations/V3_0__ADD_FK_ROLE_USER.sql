ALTER TABLE role
ADD CONSTRAINT fk_created_by_user_id FOREIGN KEY (created_by) REFERENCES user(id_user),
ADD CONSTRAINT fk_updated_by_user_id FOREIGN KEY (updated_by) REFERENCES user(id_user);