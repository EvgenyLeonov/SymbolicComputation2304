def counting_sort(lst):
	K = max(lst)
	counts = [0] * (K + 1)
	for elem in lst:
		counts[elem] += 1

	# print(counts)

	# we now overwrite our original counts with the starting index
	# of each element in the final sorted array

	starting_index = 0
	for i, count in enumerate(counts):
		counts[i] = starting_index
		starting_index += count

	print(counts)

	sorted_lst = [0] * len(lst)

	for elem in lst:
		sorted_lst[counts[elem]] = elem
		# since we have placed an item in index counts[elem], we need to
		# increment counts[elem] index by 1 so the next duplicate element
		# is placed in appropriate index
		counts[elem] += 1

	# common practice to copy over sorted list into original lst
	# it's fine to just return the sorted_lst at this point as well
	for i in range(len(lst)):
		lst[i] = sorted_lst[i]

	return lst


print(counting_sort([5, 4, 5, 5, 1, 1, 3]))
