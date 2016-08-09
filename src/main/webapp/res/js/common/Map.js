/**
 * @description JS的Map对象，仿造JDK的Map对象编写
 * @autor Nick
 * @version 1.0
 * @Date 2016/06/23
 */
function Map() {
	
	this.arr = new Array();
	
	/**
	 * @description 构造方法
	 * @param key Key值
	 * @param value Value值
	 */
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};
	
	/**
	 * @param Map的Key值
	 * @return 元素的Value值
	 */
	this.get = function(key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};
	
	/**
	 * @description 如果Map里面的Key包含传递过来的key，则返回
	 * @param key
	 * @return Array
	 */
	this.getByIndexOf = function(key) {
		var valus = new Array();
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key.indexOf(key) > 0) {
				valus.push(this.arr[i].value);
			}
		}
		return valus;
	};
	
	/**
	 * @description 加入元素
	 * @param key
	 * @param value
	 */
	this.put = function(key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};
	
	/**
	 * @description 根据key删除某个元素
	 * @param key
	 */
	this.remove = function(key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	};
	
	/**
	 * @description 大小
	 * @return Map的长度
	 */
	this.size =  function() {
		return this.arr.length;
	};
	
	/**
	 * @description 是否为空
	 * @return 是否为空
	 */
	this.isEmpty = function() {
		return this.arr.length <= 0;
	};
	
	/**
	 * @description 得到Map里面的所有key的List
	 * @return Map的所有Key
	 */
	this.keys = function() {
		var keys = new Array();
		for(var i = 0; i < this.arr.length; i++) {
			keys.push(this.arr[i].key);
		}
		return keys;
	};
	
	/**
	 * @description 得到Map里面的所有value的List
	 * @return 所有Values
	 */
	this.values = function() {
		var values = new Array();
		for(var i = 0; i < this.arr.length; i++) {
			values.push(this.arr[i].value);
		}
		return values;
	};
	
	/**
	 * @description 是否包含某个Key
	 * @return 是否为空
	 */
	this.containsKey = function(key) {
		if(key == null) {
			return false;
		}
		return this.get(key) != null;
	};
	
	/**
	 * @description 是否包含某个Value
	 * @return 是否包含某个value
	 */
	this.containValue = function(value) {
		if (value == null) {
			return false;
		}
		for(var i = 0; i < this.arr.length; i++) {
			if(this.arr[i].value == value) {
				return true;
			}
		}
		return false;
	};
	
	/**
	 * @description 清空元素	
	 */
	this.clear = function() {
		//注意，此处不能直接arr = null;
		for (var i = 0; i < this.arr.length; i++) {
			this.arr[i] = null;
		}
	}
	
}