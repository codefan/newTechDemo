def runOpt(dataSet):
  ret = {}
  ret['cust']  = dataSet['cust'].size()
  ret['city']  = dataSet['city'].size()
  return {'ret': [ret]}
