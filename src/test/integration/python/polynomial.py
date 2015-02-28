import array
import numpy
class polynomial:
	"""polynomial:
	Класс для аппроксимации функции полиномом заданной степени по методу наименьших квадратов.
	Для использования этого класса должен быть установлен numpy.
	"""
	def __init__(self, xval, yval, amount):
		""" __init__(self, xval, yval, amount):
		Input: xval - массив координат X аппроксимированных точек,
			yval - массив координат X аппроксимированных точек,
			amount - степень полинома.
		"""
		self.xval = xval
		self.yval = yval
		self.amount = amount
		assert len(xval) == len(yval)
		n = amount + 1
		a = numpy.zeros ((n,n), "d")
		b = numpy.array ([0.0] * n, "d")
		c = numpy.array ([0.0] * 2 * n, "d")
		self.coefficients = numpy.array ([0.0] * n, "d")
		for i in range (len(xval)):
			x = self.xval[i]
			y = self.yval[i]
			f = 1.0
			for j in range(2 * n):
				if j < n:
					b[j] = b[j] + y
					y = y * x
				c[j] = c[j] + f
				f = f * x
		#4
		for i in range (n):
			k = i

			for j in range (n):
				a[i][j] = c[k]
				k = k + 1
		# 5
		for i in range (n):
			for j in range (i + 1, n):
				a[j][i] = a[j][i] / -a[i][i]
				for k in range (i + 1, n):
					a[j][k] = a[j][k] + a[j][i] * a[i][k]

				b[j] = b[j] + a[j][i] * b[i]
		# 6
		self.coefficients[n - 1] = b[n - 1] / a[n - 1][n - 1];
		# 7
		for i in range (n - 2, -1, -1):
			h = b[i]
			for j in range (i + 1, n):
				h = h - self.coefficients[j] * a[i][j]
			self.coefficients[i] = h / a[i][i]
	def getval (self, xpoint):
		"""getval (self, xpoint):
		Input: xpoint - координата X, в которой надо узнать аппроксимированное значение функции.
		Возвращает значение координаты Y.
		"""
		s = 0.0
		for i in range (self.amount, 0, -1):
			s = (s + self.coefficients[i]) * xpoint
		return s + self.coefficients[0]
