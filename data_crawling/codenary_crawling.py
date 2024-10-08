from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd

driver = webdriver.Chrome()
driver.get("https://www.codenary.co.kr/techstack/list")

last_height = driver.execute_script("return document.body.scrollHeight")

while True:
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
    time.sleep(2)
    new_height = driver.execute_script("return document.body.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height

data = []

items = driver.find_elements("css selector", '.mantine-Grid-col.mantine-1vi3lr2')

for item in items:
    stack_name = item.find_element("class name", 'mantine-xn0h5k').text
    stack_type = item.find_element("class name", 'mantine-8ytu7x').text
    image_url = item.find_element("tag name", 'img').get_attribute('src')
    data.append([stack_name, stack_type, image_url])

df = pd.DataFrame(data, columns=['stack_name', 'stack_type', 'image_url'])
df.to_csv('stack_dataset.csv', index=False, encoding='utf-8-sig')

driver.quit()
