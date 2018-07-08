from flask_script import Manager
from flask_script import prompt_bool

from app_config import create_app
from env_config import ConfigEnum
from extensions import db

app = create_app(ConfigEnum.DEVELOPMENT)

manager = Manager(app)


@manager.command
def initdb():
    "创建数据库表结构"

    if prompt_bool("您确定要重新创建数据库表结构?"):
        db.create_all()


@manager.command
def dropdb():
    "删除所有表"

    if prompt_bool("您确定要删除数据库所有表?"):
        db.drop_all()


# manager.add_command("start", Server(host='127.0.0.1', port=80, use_debugger=True))

if __name__ == '__main__':
    # manager.run()
    app.run(host='0.0.0.0', port=80, debug=True)
