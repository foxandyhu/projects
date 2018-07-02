from wtforms import Form, StringField, PasswordField, validators, IntegerField


class UserLoginForm(Form):
    """用户登录表单验证"""

    username = StringField("username", [validators.Length(min=5, max=20, message="用户名长度为5-20之间!"),
                                        validators.DataRequired("请输入用户名!")])
    password = PasswordField("password", [validators.DataRequired("请输入密码!"),
                                          validators.Length(min=5, max=20, message="密码长度为5-20之间!")])
    captch = StringField("captch", [validators.DataRequired("请输入验证码!")])


class MembersForm(Form):
    """用户表单验证"""

    username = StringField("username", [validators.Length(min=6, max=50, message="用户名长度为6-50之间!"),
                                        validators.DataRequired("请输入用户名!")])
    password = PasswordField("password", [validators.DataRequired("请输入密码!"),
                                          validators.Length(min=6, max=30, message="密码长度为6-30之间!")])
    nickname = StringField("nickname",
                           [validators.Length(min=1, max=15, message="昵称长度为1-15之间!"),
                            validators.DataRequired("请输入昵称!")])


class TagsForm(Form):
    """文章标签验证"""

    name = StringField("name", [validators.Length(min=1, max=10, message="标签名称长度为1-10之间!"),
                                validators.DataRequired("请输入标签名称!")])


class CategorysForm(Form):
    """文章类别验证"""

    name = StringField("name", [validators.Length(min=1, max=10, message="类别名称长度为1-10之间!"),
                                validators.DataRequired("请输入类别名称!")])


class ArticlesForm(Form):
    """文章验证"""

    title = StringField("title", [validators.Length(min=1, max=50, message="标题长度为1-50之间!"),
                                  validators.DataRequired("请输入文章标题!")])
    summary = StringField("summary", [validators.Length(min=1, max=200, message="摘要长度为1-200之间!"),
                                      validators.DataRequired("请输入文章摘要!")])
    content = StringField("content", [validators.DataRequired("请输入文章内容!")])
    seq = IntegerField("seq", [validators.NumberRange(min=0, message="排序序号错误!")])
    category_id = IntegerField("category_id",
                               [validators.DataRequired("请选择文章类别"), validators.NumberRange(min=1, message="请选择文章类别!")])
