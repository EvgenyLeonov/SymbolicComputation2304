class RouteInfo:
	def __init__(self, city1, city2, prices):
		self.city1 = city1
		self.city2 = city2
		self.prices = prices

	def suitable_for_organized_tours(self):
		for p in self.prices:
			if p >= 1000:
				return True
		return False
