Run program:

java -jar smart-xml-analyzer.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>


Algorithm : 
1. Find target element.
2. Get all attributes from target element.
3. Get all elements which matched attributes in other file.
4. Find most similar element (brute force) and return his.
5. Get path to similar element.


Simple output:

sample-1-evil-gemini.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[1]

sample-2-container-and-clone.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0]

sample-3-the-escape.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]

sample-4-the-mash.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]

