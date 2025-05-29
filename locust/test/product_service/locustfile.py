from locust import HttpUser, task, between

class ProductServiceUser(HttpUser):
    wait_time = between(1, 3)

    @task
    def get_all_products(self):
        path = "/product-service/api/products"
        with self.client.get(path, catch_response=True, name="/api/products") as response:
            if response.status_code >= 200 and response.status_code < 300:
                response.success()
            else:
                response.failure(f"Unexpected status code: {response.status_code}")