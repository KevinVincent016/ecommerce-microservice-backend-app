# locustfile.py
from locust import HttpUser, task, between

#from test.favourite_service.locustfile import FavouriteServiceUser
from test.product_service.locustfile import ProductServiceUser
from test.payment_service.locustfile import PaymentServiceUser
from test.order_service.locustfile import OrderServiceUser

