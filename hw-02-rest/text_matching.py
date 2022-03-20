def kmp(text, pattern):
    result = []

    pi = prefix_function(pattern)
    q = 0

    for i in range(len(text)):
        while q > 0 and pattern[q] != text[i]:
            q = pi[q - 1]

        if pattern[q] == text[i]:
            q += 1

        if q == len(pattern):
            result.append(i + 1 - q)
            q = pi[q - 1]

    return result


def prefix_function(pattern):
    pi = [0]
    k = 0

    for q in range(1, len(pattern)):
        while k > 0 and pattern[k] != pattern[q]:
            k = pi[k - 1]

        if pattern[k] == pattern[q]:
            k += 1

        pi.append(k)

    return pi
