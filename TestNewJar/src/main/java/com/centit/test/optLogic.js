function runOpt(dataSet) {
  var item = {};
  item.cust = dataSet.cust.length
  item.city = dataSet.city.length
  return {'ret': [item,item]}
}
