.text 0x00400000
.globl main
main:
li $v0, 5
syscall
move $t1, $v0
li $v0, 5
syscall
move $t2, $v0
bge $t1, $t2, t1
j t2
t1:
move $a0, $t1
j end
t2:
move $a0, $t2
end:
li $v0, 1
syscall
li $v0, 10
syscall