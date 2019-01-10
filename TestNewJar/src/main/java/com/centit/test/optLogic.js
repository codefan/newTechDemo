var runOpt = {
  run: function() {
    item = {}
    item.cust = dataSet.cust.length
    item.city = dataSet.city.length
    data = []
    data.push(item)
    ret = {}
    ret.ret = data
    return ret;
  }
}
