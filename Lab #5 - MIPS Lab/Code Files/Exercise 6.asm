.text 0x00400000
.globl main
main:
li $t0, 1
li $t1, 10
loop:
bgt $t0, $t1, end
move $a0, $t0
li $v0, 1
syscall
la $a0, nl
li $v0, 4
syscall
addu $t0, $t0, 1
j loop
end:
li $v0, 10
syscall
.data
nl:
.asciiz "\n"