from wtforms import Form, StringField, PasswordField, validators


class MembersForm(Form):
    """用户表单验证"""

    username = StringField("username", [validators.Length(min=6, max=50, message="用户名长度为6-50之间!"),
                                        validators.DataRequired("请输入用户名!")])
    password = PasswordField("password", [validators.DataRequired("请输入密码!"),
                                          validators.Length(min=6, max=30, message="密码长度为6-30之间!")])
    nickname = StringField("nickname",
                           [validators.Length(min=1, max=15, message="昵称长度为1-15之间!"), validators.DataRequired("请输入昵称!")])


class TagsForm(Form):
    """文章标签验证"""

    name = StringField("name", [validators.Length(min=1, max=10, message="标签名称长度为1-10之间!"),
                                validators.DataRequired("请输入标签名称!")])
