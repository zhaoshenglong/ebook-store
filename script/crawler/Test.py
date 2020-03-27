from typing import List


def solution(a: List):
    sz = len(a)
    res = [a[0]]
    for i in range(1, sz):
        if a[i] > res[-1]:
            res.append(a[i])
        else:
            if i > 1 and a[i] > res[-2]:
                res[-1] = a[i]
    return res


def main():
    print(solution([2, 1, 5, 3, 6, 4, 8, 9, 7]))


if __name__ == '__main__':
    main()
