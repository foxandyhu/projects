class Market(object):
    def __init__(self,title='tt',count=22):
        self.title = title
        self.count = count

    def list_all_member(self):
        for name, value in vars(self).items():
            print('%s=%s' % (name, value))


if __name__ == '__main__':
    market = Market()
    for name, value in vars(market).items():
        print('%s=%s' % (name, value))
